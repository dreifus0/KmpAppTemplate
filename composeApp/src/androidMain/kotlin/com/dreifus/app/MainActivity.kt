package com.dreifus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dreifus.app.di.PlatformDependencies

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val platformDeps = PlatformDependencies(applicationContext)
        setContent {
            App(platformDeps)
        }
    }
}
