package com.dreifus.app.counter.mvu

import com.yavorcool.mvucore.filteringHandler
import kotlinx.coroutines.delay

val LoadIncrementHandler =
    filteringHandler<CounterCommand.LoadIncrement, CounterCommand, CounterEvent>(
        cancelPreviousOnNewCommand = true,
    ) { command ->
        delay(1000)
        CounterEvent.AsyncResult(command.currentValue + 1)
    }
