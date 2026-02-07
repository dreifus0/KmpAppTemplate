package com.dreifus.app.counter.data

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.delay

interface CounterRepository {
    suspend fun incrementRemote(currentValue: Int): Int
}

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class CounterRepositoryImpl : CounterRepository {
    override suspend fun incrementRemote(currentValue: Int): Int {
        delay(1000) // simulate network
        return currentValue + 1
    }
}
