package com.dreifus.app

import android.util.Log
import com.dreifus.helpers.ResourceManager
import com.dreifus.template.di.common.metro.MetroAndroidApp
import com.dreifus.template.di.common.metro.MetroApp
import com.dreifus.template.di.common.metro.createGraphAndInject
import com.google.android.play.core.splitcompat.SplitCompatApplication
import dev.zacsweers.metro.Inject

@MetroAndroidApp
class App : SplitCompatApplication(), MetroApp {

    @Inject
    lateinit var resources: ResourceManager

    override val appGraph = createGraphAndInject()

    override fun onCreate() {
        super.onCreate()
        val kek = resources.getString(R.string.app_name)
        Log.d("Kek", kek)
    }
}
