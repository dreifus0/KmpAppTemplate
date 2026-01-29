package com.dreifus.template.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import java.io.BufferedWriter

class ScreensProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }
        val screenClasses = buildList {
            resolver.getAllFiles().forEach { file ->
                addAll(
                    file.declarations
                        .filterIsInstance<KSClassDeclaration>()
                        .filter { it.simpleName.asString().endsWith("Screen") }
                )
            }
        }
        if (screenClasses.isNotEmpty()) {
            @OptIn(KspExperimental::class)
            val moduleName = resolver.getModuleName()
            val modulePackageName = environment.options["androidNamespace"]
                ?: ("com.dreifus.screen." + moduleName.asString().replace('-', '.'))
            val sources = screenClasses.mapNotNull { it.containingFile }.distinct().toTypedArray()
            val file = environment.codeGenerator.createNewFile(
                dependencies = Dependencies(true, *sources),
                packageName = modulePackageName,
                fileName = "ScreensModule",
                extensionName = "kt",
            )
            file.bufferedWriter().use { writer ->
                writer.write(daggerModuleHeader(modulePackageName))

                screenClasses.forEach { screen ->
                    writeScreenInfoProvider(screen, writer)
                }

                writer.write("\n}\n")
            }
        }
        invoked = true
        return emptyList()
    }

    private fun writeScreenInfoProvider(screen: KSClassDeclaration, writer: BufferedWriter) {
        screen.qualifiedName?.apply {
            val screenInfo = screen.annotations.find { it.shortName.asString() == "ScreenInfo" }
            val qualifiedName = asString()
            val name = screenInfo?.arguments?.find {
                it.name?.asString() == "name"
            }?.value?.toString().orEmpty().ifEmpty {
                getShortName().removeSuffix("Screen")
            }
            val valueTeam = (
                    screenInfo?.arguments
                        ?.find { it.name?.asString() == "valueTeam" }
                        ?.value?.toString()?.takeIf { it.isNotEmpty() }
                        ?: environment.options["valueTeam"]
                        ?: ""
                    ).let { "\"$it\"" }
            writer.write("\n\n")
            writer.write(
                """
                    @Provides
                    @IntoMap
                    @ScreenClassKey($qualifiedName::class)
                    fun provide${name}ScreenInfo(): ScreenInfo = ScreenInfo(
                        name = "$name",
                        valueTeam = $valueTeam,
                    )
                """.replaceIndent("    ")
            )
        }
    }

    private fun daggerModuleHeader(modulePackageName: String) = """
        package $modulePackageName

        import com.dreifus.navigation.ScreenClassKey
        import com.dreifus.navigation.ScreenInfo
        import dev.zacsweers.metro.Provides
        import dev.zacsweers.metro.ContributesTo
        import dev.zacsweers.metro.AppScope
        import dev.zacsweers.metro.ClassKey
        import dev.zacsweers.metro.IntoMap

        @ContributesTo(AppScope::class)
        interface ScreensModule {
    """.trimIndent()
}

class ScreensProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return ScreensProcessor(environment)
    }
}
