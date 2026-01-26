package com.dreifus.template.di.common

import dev.zacsweers.metro.MapKey
import dev.zacsweers.metro.MembersInjector
import kotlin.reflect.KClass

/** A [MapKey] annotation for binding ViewModels in a multibinding map. */
@MapKey
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MembersInjectorKey(val value: KClass<out MembersInjector<*>>)
