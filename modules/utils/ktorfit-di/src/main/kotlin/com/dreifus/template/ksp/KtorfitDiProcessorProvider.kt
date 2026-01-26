package com.dreifus.template.ksp

import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration

private val annotations = listOf(
    "de.jensklingenberg.ktorfit.http.GET",
    "de.jensklingenberg.ktorfit.http.POST",
    "de.jensklingenberg.ktorfit.http.DELETE",
    "de.jensklingenberg.ktorfit.http.PUT",
    "de.jensklingenberg.ktorfit.http.PATCH",
)

class KtorfitDiProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }

        val apiClasses = annotations.flatMap { resolver.getSymbolsWithAnnotation(it) }
            .filterIsInstance<KSFunctionDeclaration>()
            .mapNotNull { it.closestClassDeclaration() }
            .distinct()

        for (apiClass in apiClasses) {
            val sources = apiClass.containingFile ?: continue
            val serviceName = apiClass.simpleName.asString()
            val modulePackageName = apiClass.packageName.asString()
            val file = environment.codeGenerator.createNewFile(
                dependencies = Dependencies(true, sources),
                packageName = modulePackageName,
                fileName = serviceName + "Module",
                extensionName = "kt",
            )
            file.bufferedWriter().use { writer ->
                writer.write(diModule(modulePackageName, serviceName))
            }
        }
        invoked = true
        return emptyList()
    }

    private fun diModule(modulePackageName: String, serviceName: String) = """
        package $modulePackageName

        import com.dreifus.template.network.di.ServiceClassKey
        import de.jensklingenberg.ktorfit.internal.ClassProvider
        import dev.zacsweers.metro.AppScope
        import dev.zacsweers.metro.ContributesTo
        import dev.zacsweers.metro.IntoMap
        import dev.zacsweers.metro.Provides

        @ContributesTo(AppScope::class)
        interface ${serviceName}Module {
            @Provides
            @IntoMap
            @ServiceClassKey(${serviceName}::class)
            fun provide${serviceName}(): ClassProvider<*> = _${serviceName}Provider()
        }
    """.trimIndent()
}

class KtorfitDiProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return KtorfitDiProcessor(environment)
    }
}
