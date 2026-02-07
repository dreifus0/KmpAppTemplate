package com.dreifus.app.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.counter.data.CounterRepository
import com.dreifus.app.counter.mvu.CounterEvent
import com.dreifus.app.counter.mvu.CounterState
import com.dreifus.app.counter.mvu.CounterUpdate
import com.dreifus.app.counter.mvu.LoadIncrementHandler
import com.dreifus.template.di.common.metro.viewmodel.ViewModelKey
import com.dreifus.template.di.common.metro.viewmodel.ViewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import com.yavorcool.mvucore.impl.Store

@ViewModelKey(CounterViewModel::class)
@ContributesIntoMap(ViewModelScope::class)
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
