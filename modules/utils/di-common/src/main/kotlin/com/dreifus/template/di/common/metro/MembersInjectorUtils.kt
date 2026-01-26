package com.dreifus.template.di.common.metro

import android.app.Service
import com.dreifus.template.di.common.metro.activity.MetroActivity
import dev.zacsweers.metro.MembersInjector
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass


inline fun <T : Any> T.inject(
    injectorClass: KClass<out MembersInjector<out T>>,
    membersInjectorsMap: Map<KClass<out MembersInjector<*>>, Provider<MembersInjector<*>>>,
) {
    val injector = membersInjectorsMap[injectorClass]
        ?: error("Injector for class $injectorClass not found. Map: $membersInjectorsMap")
    @Suppress("UNCHECKED_CAST")
    (injector() as MembersInjector<T>).injectMembers(this)
}

inline fun <T : Service> T.inject(injectorClass: KClass<out MembersInjector<T>>) {
    inject(injectorClass, (application as MetroApp).appGraph.appMembersInjectorsMap)
}

// region Stubs for code completion
/** Stub for code completion, right createGraphAndInject() should be generated in compile time. */
@Deprecated(
    "Stub for code completion, right createGraphAndInject() should be generated in compile time.",
    level = DeprecationLevel.ERROR
)
inline fun <reified T : MetroApp> T.createGraphAndInject(): MetroAppGraph = error(
    "createGraphAndInject not generated for ${T::class.simpleName}. " +
        "Check your build configuration to use ksp(projects.modules.utils.metroAndroidKsp)."
)

@Deprecated(
    "Stub for code completion, right getInjectorClass() should be generated in compile time.",
    level = DeprecationLevel.ERROR
)
inline fun <reified T : MetroActivity> T.getInjectorClass(): KClass<out MembersInjector<out MetroActivity>> = error(
    "getInjectorClass not generated for ${T::class.simpleName}. " +
        "Check your build configuration to use ksp(projects.modules.utils.metroAndroidKsp)."
)

@Deprecated(
    "Stub for code completion, right injectMembers() should be generated in compile time.",
    level = DeprecationLevel.ERROR
)
inline fun <reified T : Service> T.injectMembers(): KClass<out MembersInjector<out Service>> = error(
    "injectMembers not generated for ${T::class.simpleName}. " +
        "Check your build configuration to use ksp(projects.modules.utils.metroAndroidKsp)."
)
// endregion
