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
                // первый символ "i" в "implementation" не проверяем что бы не использовать ignoreCase
                it.name.endsWith("mplementation") ||
                    it.name.endsWith("api", ignoreCase = true)
            }.flatMap { it.dependencies.withType<ProjectDependency>() }
            prepareDependencies(dependencies)

            // Сначала проверяем транзитивные зависимости
            dependencies.forEach { projectDependency ->
                project.project(projectDependency.path).findMetroDependencies(isTransitiveOnly = true) {
                    isTransitiveOnlyChecked = true
                    // сбрасываем список проверенных зависимостей, так как проверяем заново
                    // с новым значением isTransitiveOnly.
                    dependenciesChecked.clear()
                    prepareDependencies(dependencies)

                    // Затем проверяем прямые зависимости
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
                // промечаем что модуль уже добавлен в список на проверку
                dependenciesChecked[projectDependency.path] = false
                // Что бы конфигурация проекта прилы не завершилась раньше,
                // чем конфигурация модулей, от которых он зависит.
                // Так как нужно пройти по всему дереву модулей и проверить в каждом зависимости,
                // надо дождаться пока код проверки зависомостей выполнится во всех модулях.
                // По дефолту конфигурация апп модуля завершается раньше,
                // и после этого из за кеширования тасок уже нельзя модифицировать поля таски,
                // добавляя туда текст ошибки. На времени конфигурации это по идее не отражается,
                // просто финализация конфигурации апп модуля будет дожидаться пока завершится
                // конфигурация всех зависимостей.
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
                    // первый символ "i" в "implementation" не проверяем что бы не использовать ignoreCase
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
