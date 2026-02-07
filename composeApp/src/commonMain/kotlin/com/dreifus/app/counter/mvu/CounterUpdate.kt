package com.dreifus.app.counter.mvu

import com.yavorcool.mvucore.Next
import com.yavorcool.mvucore.Update

val CounterUpdate = Update<CounterState, CounterEvent, CounterCommand, CounterEffect> { state, event ->
    when (event) {
        is CounterEvent.Increment -> Next(
            state = state.copy(count = state.count + 1),
        )

        is CounterEvent.Decrement -> Next(
            state = state.copy(count = state.count - 1),
        )

        is CounterEvent.AsyncIncrement -> Next(
            state = state.copy(isLoading = true),
            command = CounterCommand.LoadIncrement(state.count),
        )

        is CounterEvent.AsyncResult -> Next(
            state = state.copy(count = event.newValue, isLoading = false),
            effect = CounterEffect.ShowMessage("Async increment done! New value: ${event.newValue}"),
        )
    }
}
