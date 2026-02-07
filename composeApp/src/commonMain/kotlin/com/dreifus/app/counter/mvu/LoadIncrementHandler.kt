package com.dreifus.app.counter.mvu

import com.dreifus.app.counter.data.CounterRepository
import com.yavorcool.mvucore.filteringHandler

fun LoadIncrementHandler(repo: CounterRepository) =
    filteringHandler<CounterCommand.LoadIncrement, CounterCommand, CounterEvent>(
        cancelPreviousOnNewCommand = true,
    ) { command ->
        val result = repo.incrementRemote(command.currentValue)
        CounterEvent.AsyncResult(result)
    }
