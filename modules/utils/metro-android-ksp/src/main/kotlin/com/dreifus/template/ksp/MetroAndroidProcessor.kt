package com.dreifus.template.ksp

import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

class MetroAndroidProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        handleMetroAndroidApp(resolver)
        handleAndroidEntryPoint(resolver)
        return emptyList()
    }

    private fun handleAndroidEntryPoint(resolver: Resolver) {
        val entryPoints = resolver.getSymbolsWithAnnotation("com.dreifus.template.di.common.metro.AndroidEntryPoint")
            .filterIsInstance<KSClassDeclaration>()
        for (classDeclaration in entryPoints) {
            val packageName = classDeclaration.packageName.asString()
            val className = classDeclaration.simpleName.asString()
            val injectorName = className + "_MetroInjector"
            val sources = requireNotNull(classDeclaration.containingFile)
            val dependencies = Dependencies(true, sources)
            val classWithPropertyInjection = findClassWithPropertyInjection(classDeclaration)
            val superPackageName = classWithPropertyInjection.packageName.asString()
            val superClassName = classWithPropertyInjection.simpleName.asString()
            val isActivity = className.endsWith("Activity")
            val scopePackageName = if (isActivity) "com.dreifus.template.di.common.metro.activity" else "dev.zacsweers.metro"
            val scopeClassName = if (isActivity) "ActivityRetainedScope" else "AppScope"

            generateInjector(
                dependencies,
                packageName,
                injectorName,
                scopePackageName,
                scopeClassName,
                superPackageName,
                superClassName
            )
            generateInjectTool(dependencies, className, isActivity, packageName, injectorName)
        }
    }

    private fun handleMetroAndroidApp(resolver: Resolver) {
        val apps = resolver.getSymbolsWithAnnotation("com.dreifus.template.di.common.metro.MetroAndroidApp")
            .filterIsInstance<KSClassDeclaration>()
        for (classDeclaration in apps) {
            val packageName = classDeclaration.packageName.asString()
            val appClassName = classDeclaration.simpleName.asString()
            val graphName = appClassName + "_MetroGraph"
            val sources = requireNotNull(classDeclaration.containingFile)
            val dependencies = Dependencies(true, sources)
            val classWithPropertyInjection = findClassWithPropertyInjection(classDeclaration)

            generateGraph(dependencies, packageName, classWithPropertyInjection, graphName)
            generateAppUtil(dependencies, packageName, appClassName, graphName)
        }
    }

    private inline fun generateFile(
        dependencies: Dependencies,
        packageName: String,
        className: String,
        content: () -> String,
    ) {
        val file = environment.codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = packageName,
            fileName = className,
            extensionName = "kt",
        )
        file.bufferedWriter().use {
            it.write(content())
        }
    }

    private fun findClassWithPropertyInjection(appDeclaration: KSClassDeclaration): KSClassDeclaration {
        var classWithPropertyInjection = appDeclaration
        while (classWithPropertyInjection.getDeclaredProperties()
                .all { propertyDeclaration ->
                    propertyDeclaration.annotations.all { it.shortName.asString() != "Inject" }
                }
        ) {
            classWithPropertyInjection = classWithPropertyInjection.superTypes
                .map { it.resolve().declaration }
                .filterIsInstance<KSClassDeclaration>()
                .firstOrNull()
                ?: error(
                    "No class with @Inject found in the hierarchy. No parent for " +
                        classWithPropertyInjection.qualifiedName?.asString()
                )
        }
        return classWithPropertyInjection
    }

    private fun generateInjectTool(
        dependencies: Dependencies,
        className: String,
        isActivity: Boolean,
        packageName: String,
        injectorName: String,
    ) = generateFile(dependencies, "com.dreifus.template.di.common.metro", className + "_Util") {
        if (isActivity) {
            """
                package com.dreifus.template.di.common.metro

                import com.dreifus.template.di.common.metro.activity.MetroActivity
                import $packageName.$injectorName
                import $packageName.$className
                import dev.zacsweers.metro.MembersInjector
                import kotlin.reflect.KClass

                inline fun $className.getInjectorClass(): KClass<out MembersInjector<out MetroActivity>> =
                    $injectorName::class
            """.trimIndent()
        } else {
            """
                package com.dreifus.template.di.common.metro

                import $packageName.$injectorName
                import $packageName.$className
                import com.dreifus.template.di.common.metro.inject

                inline fun $className.injectMembers() = inject($injectorName::class)
            """.trimIndent()
        }
    }

    private fun generateInjector(
        dependencies: Dependencies,
        packageName: String,
        injectorName: String,
        scopePackageName: String,
        scopeClassName: String,
        superPackageName: String,
        superClassName: String,
    ) = generateFile(dependencies, packageName, injectorName) {
        """
            package $packageName

            import com.dreifus.template.di.common.MembersInjectorKey
            import $scopePackageName.$scopeClassName
            import $superPackageName.$superClassName
            import dev.zacsweers.metro.ContributesIntoMap
            import dev.zacsweers.metro.Inject
            import dev.zacsweers.metro.MembersInjector
            import dev.zacsweers.metro.binding

            @Inject
            @ContributesIntoMap($scopeClassName::class, binding<MembersInjector<*>>())
            @MembersInjectorKey($injectorName::class)
            class $injectorName(
                private val injector: MembersInjector<$superClassName>,
            ) : MembersInjector<$superClassName> by injector
        """.trimIndent()
    }

    private fun generateAppUtil(
        dependencies: Dependencies,
        packageName: String,
        appClassName: String,
        graphName: String,
    ) = generateFile(dependencies, "com.dreifus.template.di.common.metro", appClassName + "_Util") {
        """
            package com.dreifus.template.di.common.metro
            
            import $packageName.$appClassName
            import $packageName.$graphName
            import dev.zacsweers.metro.createGraphFactory
            
            inline fun $appClassName.createGraphAndInject(): $graphName {
                val graph = createGraphFactory<$graphName.Factory>().create(this)
                graph.inject(this)
                return graph
            }
        """.trimIndent()
    }

    private fun generateGraph(
        dependencies: Dependencies,
        packageName: String,
        classWithInjectionsDeclaration: KSClassDeclaration,
        graphName: String,
    ) = generateFile(dependencies, packageName, graphName) {
        """
            package $packageName
            
            import android.app.Application
            import com.dreifus.template.di.common.metro.MetroAppGraph
            import dev.zacsweers.metro.AppScope
            import dev.zacsweers.metro.DependencyGraph
            import dev.zacsweers.metro.Provides
            import ${classWithInjectionsDeclaration.qualifiedName?.asString()}
            
            @DependencyGraph(scope = AppScope::class)
            interface $graphName : MetroAppGraph {
                fun inject(app: ${classWithInjectionsDeclaration.simpleName.asString()})
            
                @DependencyGraph.Factory
                fun interface Factory {
                    fun create(
                        @Provides application: Application,
                    ): $graphName
                }
            }
        """.trimIndent()
    }
}

class MetroAndroidProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return MetroAndroidProcessor(environment)
    }
}
