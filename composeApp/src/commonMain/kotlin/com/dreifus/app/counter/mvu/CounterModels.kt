package com.dreifus.app.counter.mvu

data class CounterState(
    val count: Int = 0,
    val isLoading: Boolean = false,
)

sealed interface CounterEvent {
    data object Increment : CounterEvent
    data object Decrement : CounterEvent
    data object AsyncIncrement : CounterEvent
    data class AsyncResult(val newValue: Int) : CounterEvent
}

sealed interface CounterCommand {
    data class LoadIncrement(val currentValue: Int) : CounterCommand
}

sealed interface CounterEffect {
    data class ShowMessage(val message: String) : CounterEffect
}
