package com.dreifus.app.counter.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreifus.app.counter.mvu.CounterEffect
import com.dreifus.app.counter.mvu.CounterEvent
import com.dreifus.app.counter.mvu.CounterState
import com.dreifus.app.counter.mvu.CounterUpdate
import com.dreifus.app.counter.mvu.LoadIncrementHandler
import com.dreifus.navigation.ui.RootScreenWithTabs
import com.dreifus.template.uikit.style.AppTheme
import com.yavorcool.mvucore.impl.Store

class CounterScreen : RootScreenWithTabs {

    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val store = remember {
            Store<CounterState, CounterEvent, CounterEvent, _, _>(
                initialState = CounterState(),
                update = CounterUpdate,
                commandHandlers = listOf(LoadIncrementHandler),
            )
        }
        LaunchedEffect(store) { store.launch(scope) }

        val state by store.state.collectAsState()
        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(store) {
            store.effects.collect { effect ->
                when (effect) {
                    is CounterEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
                }
            }
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Count: ${state.count}",
                    style = AppTheme.typography.headlineLarge,
                    color = AppTheme.colors.contentPrimary,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { store.dispatch(CounterEvent.Decrement) }) {
                        Text("-1")
                    }
                    Button(onClick = { store.dispatch(CounterEvent.Increment) }) {
                        Text("+1")
                    }
                    Button(
                        onClick = { store.dispatch(CounterEvent.AsyncIncrement) },
                        enabled = !state.isLoading,
                    ) {
                        Text("Async +1")
                    }
                }

                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}
