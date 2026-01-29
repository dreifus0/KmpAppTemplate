package com.dreifus.gradle.utils

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.kotlin.dsl.withType

open class MetroCheckDependenciesTask : DefaultTask() {

    @Input
    var errorText: String = ""

    @TaskAction
    fun execute() {
        if (errorText.isNotEmpty()) {
            logger.error("e: $errorText")
            state.addFailure(TaskExecutionException(this, RuntimeException(errorText)))
        }
    }

    fun checkMetroDependencies() {
        DependenciesChecker().checkMetroDependencies()
    }

    inner class DependenciesChecker {
        private val dependenciesWithMetro = mutableMapOf<String, Boolean>()
        private val dependenciesChecked = mutableMapOf<String, Boolean>()

        private var isTransitiveOnlyChecked = false

        fun checkMetroDependencies() {
            val dependencies = project.configurations.filter {
                // skip first char "i" in "implementation" to avoid using ignoreCase
                it.name.endsWith("mplementation") ||
                    it.name.endsWith("api", ignoreCase = true)
            }.flatMap { it.dependencies.withType<ProjectDependency>() }
            prepareDependencies(dependencies)

            // First check transitive dependencies
            dependencies.forEach { projectDependency ->
                project.project(projectDependency.path).findMetroDependencies(isTransitiveOnly = true) {
                    isTransitiveOnlyChecked = true
                    // reset list of checked dependencies since we're checking again
                    // with new isTransitiveOnly value.
                    dependenciesChecked.clear()
                    prepareDependencies(dependencies)

                    // Then check direct dependencies
                    dependencies.forEach { projectDependency ->
                        project.project(projectDependency.path).findMetroDependencies(isTransitiveOnly = false) {
                            dependenciesWithMetro.entries.removeAll { it.value }
                            completeDependenciesAnalyze(dependenciesWithMetro.keys)
                        }
                    }
                }
            }
        }

        private fun prepareDependencies(dependencies: List<ProjectDependency>) {
            dependencies.forEach { projectDependency ->
                // mark that module is already added to check list
                dependenciesChecked[projectDependency.path] = false
                // To prevent app project configuration from completing earlier
                // than configuration of modules it depends on.
                // Since we need to traverse the entire module tree and check dependencies in each,
                // we need to wait for dependency checking code to execute in all modules.
                // By default, app module configuration completes earlier,
                // and after that due to task caching, task fields can no longer be modified
                // to add error text. This shouldn't affect configuration time,
                // just finalization of app module configuration will wait for
                // configuration of all dependencies to complete.
                project.evaluationDependsOn(projectDependency.path)
            }
        }

        fun completeDependenciesAnalyze(dependenciesWithMetro: Set<String>) {
            if (dependenciesWithMetro.isNotEmpty()) {
                val dependencies = dependenciesWithMetro.sorted().map {
                    val module = it.removePrefix(":")
                        .replace(':', '.')
                        .replace(Regex("-([a-z])")) {
                            it.groups[1]?.value?.uppercase() ?: it.value
                        }
                    "implementation(projects.$module)"
                }
                val text = "Modules use metro plugin, but dont added to project " +
                    "${project.path} dependencies:\n\t" + dependencies.joinToString(separator = "\n\t")
                project.logger.error("e: $text")
                errorText = text
            }
        }

        private fun Project.findMetroDependencies(
            isTransitiveOnly: Boolean,
            onAllFound: () -> Unit,
        ) {
            fun checkInnerDependencies() {
                val isThisPassNotCompleted = isTransitiveOnlyChecked xor isTransitiveOnly
                if (!isThisPassNotCompleted) {
                    return
                }

                val hasMetroDep = plugins.hasPlugin("dev.zacsweers.metro")
                if (hasMetroDep) {
                    val oldValue = dependenciesWithMetro[path]
                    if (oldValue == null || isTransitiveOnly) {
                        dependenciesWithMetro[path] = isTransitiveOnly
                    }
                }
                val dependencies = configurations.filter {
                    // skip first char "i" in "implementation" to avoid using ignoreCase
                    (!isTransitiveOnly && it.name.endsWith("mplementation")) ||
                        it.name.endsWith("api", ignoreCase = true)
                }.flatMap { configuration ->
                    configuration.dependencies
                        .withType<ProjectDependency>()
                        .filter { dependenciesChecked[it.path] == null }
                }
                prepareDependencies(dependencies)
                dependencies.forEach { projectDependency ->
                    project(projectDependency.path).findMetroDependencies(isTransitiveOnly, onAllFound)
                }
                dependenciesChecked[path] = true
                if (dependenciesChecked.values.all { it }) {
                    isTransitiveOnlyChecked = true
                    onAllFound()
                }
            }

            if (state.executed) {
                checkInnerDependencies()
            } else {
                afterEvaluate {
                    checkInnerDependencies()
                }
            }
        }
    }

    companion object {
        private const val METRO_TASK_NAME = "metroCheckDependencies"

        fun registerFor(project: Project) = with(project) {
            val rootTaskName = METRO_TASK_NAME + "All"
            val rootTask = rootProject.tasks.let {
                it.findByName(rootTaskName) ?: it.register(rootTaskName) {
                    group = "verification"
                }
            }

            afterEvaluate {
                val task = tasks.register(METRO_TASK_NAME, MetroCheckDependenciesTask::class.java) {
                    group = "verification"
                }
                tasks.getByName("preBuild") { dependsOn += rootTask }
                rootProject.tasks.getByName(rootTaskName) { dependsOn += task }
                task.get().checkMetroDependencies()
            }
        }
    }
}
