package com.dreifus.template.di.common.metro.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.CreationExtras
import com.dreifus.template.di.common.metro.MetroApp
import com.dreifus.template.di.common.metro.inject
import com.dreifus.template.di.common.metro.viewmodel.MetroViewModelFactory
import dev.zacsweers.metro.HasMemberInjections
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.MembersInjector
import kotlin.reflect.KClass

@HasMemberInjections
abstract class MetroActivity : AppCompatActivity() {

    abstract val membersInjectorClass: KClass<out MembersInjector<out MetroActivity>>

    @Inject
    internal lateinit var metroViewModelFactory: MetroViewModelFactory

    protected val activityRetainedGraph: ActivityRetainedGraph by lazy {
        val activityRetainedViewModel = getActivityRetainedViewModel(
            factory = defaultViewModelProviderFactory
        )
        activityRetainedViewModel.activityRetainedGraph
            ?: (application as MetroApp).appGraph.createActivityRetainedGraph().also {
                activityRetainedViewModel.activityRetainedGraph = it
            }
    }

    @CallSuper
    protected open fun onInject() {
        inject(membersInjectorClass, activityRetainedGraph.activityMembersInjectorsMap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onInject()
        super.onCreate(savedInstanceState)
    }

    class ActivityRetainedViewModel : ViewModel() {
        var activityRetainedGraph: ActivityRetainedGraph? = null
    }

    private fun ViewModelStoreOwner.getActivityRetainedViewModel(
        key: String? = null,
        factory: ViewModelProvider.Factory? = null,
        extras: CreationExtras =
            if (this is HasDefaultViewModelProviderFactory) {
                this.defaultViewModelCreationExtras
            } else {
                CreationExtras.Empty
            },
    ): ActivityRetainedViewModel {
        val provider =
            if (factory != null) {
                ViewModelProvider.create(this.viewModelStore, factory, extras)
            } else if (this is HasDefaultViewModelProviderFactory) {
                ViewModelProvider.create(
                    this.viewModelStore,
                    this.defaultViewModelProviderFactory,
                    extras
                )
            } else {
                ViewModelProvider.create(this)
            }
        return if (key != null) {
            provider[key, ActivityRetainedViewModel::class]
        } else {
            provider[ActivityRetainedViewModel::class]
        }
    }
}
