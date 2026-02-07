package com.dreifus.app.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.counter.data.CounterRepository
import com.dreifus.app.counter.mvu.CounterEvent
import com.dreifus.app.counter.mvu.CounterState
import com.dreifus.app.counter.mvu.CounterUpdate
import com.dreifus.app.counter.mvu.LoadIncrementHandler
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import com.yavorcool.mvucore.impl.Store

@ViewModelKey(CounterViewModel::class)
@ContributesIntoMap(AppScope::class)
@Inject
class CounterViewModel(repo: CounterRepository) : ViewModel() {
    val store = Store<CounterState, CounterEvent, CounterEvent, _, _>(
        initialState = CounterState(),
        update = CounterUpdate,
        commandHandlers = listOf(LoadIncrementHandler(repo)),
    )

    init {
        store.launch(viewModelScope)
    }
}
