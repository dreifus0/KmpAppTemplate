package com.dreifus.helpers

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@SingleIn(AppScope::class)
class SnackbarManager @Inject constructor() {

    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 10)
    val messages: SharedFlow<String> = _messages

    fun showMessage(message: String?) {
        if (message.isNullOrBlank()) return
        _messages.tryEmit(message)
    }
}
