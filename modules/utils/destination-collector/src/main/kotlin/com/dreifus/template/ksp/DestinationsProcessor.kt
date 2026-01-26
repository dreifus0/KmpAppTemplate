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

class DestinationsProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    private var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }
        val destinationClasses = buildList {
            resolver.getAllFiles().forEach { file ->
                addAll(
                    file.declarations
                        .filterIsInstance<KSClassDeclaration>()
                        .filter { it.simpleName.asString().endsWith("Destination") }
                )
            }
        }
        if (destinationClasses.isNotEmpty()) {
            @OptIn(KspExperimental::class)
            val moduleName = resolver.getModuleName()
            val modulePackageName = environment.options["androidNamespace"]
                ?: ("com.dreifus.template.destination." + moduleName.asString().replace('-', '.'))
            val sources = destinationClasses.mapNotNull { it.containingFile }.distinct().toTypedArray()
            val file = environment.codeGenerator.createNewFile(
                dependencies = Dependencies(true, *sources),
                packageName = modulePackageName,
                fileName = "DestinationsModule",
                extensionName = "kt",
            )
            file.bufferedWriter().use { writer ->
                writer.write(daggerModuleHeader(modulePackageName))

                destinationClasses.forEach { destination ->
                    writeDestinationInfoProvider(destination, writer)
                }

                writer.write("\n}\n")
            }
        }
        invoked = true
        return emptyList()
    }

    private fun writeDestinationInfoProvider(destination: KSClassDeclaration, writer: BufferedWriter) {
        destination.qualifiedName?.apply {
            val destinationInfo = destination.annotations.find { it.shortName.asString() == "DestinationInfo" }
            val qualifiedName = asString()
            val name = destinationInfo?.arguments?.find {
                it.name?.asString() == "name"
            }?.value?.toString().orEmpty().ifEmpty {
                getShortName().removeSuffix("Destination")
            }
            val valueTeam = (
                destinationInfo?.arguments
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
                    @DestinationClassKey($qualifiedName::class)
                    fun provide${name}DestinationInfo(): DestinationInfo = DestinationInfo(
                        name = "$name",
                        valueTeam = $valueTeam,
                    )
                """.replaceIndent("    ")
            )
        }
    }

    private fun daggerModuleHeader(modulePackageName: String) = """
        package $modulePackageName

        import com.dreifus.template.core.navigation2.DestinationClassKey
        import com.dreifus.template.core.navigation2.DestinationInfo
        import dev.zacsweers.metro.Provides
        import dev.zacsweers.metro.ContributesTo
        import dev.zacsweers.metro.AppScope
        import dev.zacsweers.metro.ClassKey
        import dev.zacsweers.metro.IntoMap

        @ContributesTo(AppScope::class)
        interface DestinationsModule {
    """.trimIndent()
}

class DestinationsProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment,
    ): SymbolProcessor {
        return DestinationsProcessor(environment)
    }
}
