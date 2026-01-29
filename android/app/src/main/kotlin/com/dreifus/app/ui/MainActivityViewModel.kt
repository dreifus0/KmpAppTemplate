package com.dreifus.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dreifus.app.ui.mvu.MainActivityEffectHandler
import com.dreifus.app.ui.mvu.MainActivityEvent
import com.dreifus.app.ui.mvu.MainActivityState
import com.dreifus.app.ui.mvu.MainActivityUpdate
import com.dreifus.template.di.common.metro.viewmodel.ViewModelKey
import com.dreifus.template.di.common.metro.viewmodel.ViewModelScope
import com.yavorcool.mvucore.impl.Store
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject

@ContributesIntoMap(ViewModelScope::class)
@ViewModelKey(MainActivityViewModel::class)
class MainActivityViewModel @Inject constructor(
    update: MainActivityUpdate,
    val effectHandler: MainActivityEffectHandler,
) : ViewModel() {

    val store = Store(
        initialState = MainActivityState,
        update = update,
        commandHandlers = emptyList()
    )

    init {
        store.launch(viewModelScope)
        store.dispatch(MainActivityEvent.Init)
    }
}