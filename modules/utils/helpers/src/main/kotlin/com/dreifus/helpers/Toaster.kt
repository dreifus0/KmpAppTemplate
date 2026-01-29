package com.dreifus.helpers

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.dreifus.template.di.common.ApplicationContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import java.util.concurrent.CopyOnWriteArrayList

@SingleIn(AppScope::class)
class Toaster @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val handler = Handler(Looper.getMainLooper())
    private val messageList = CopyOnWriteArrayList<String>()

    fun showMessage(message: String?, isLong: Boolean = true) {
        message?.let { showMessageInternal(it, isLong) }
    }

    fun showMessage(@StringRes messageId: Int, isLong: Boolean = true) =
        showMessageInternal(context.getString(messageId), isLong)

    private fun showMessageInternal(message: String, isLong: Boolean) {
        when {
            message.isBlank() -> {
                return
            }

            messageList.lastOrNull() == message -> {
                Log.i("Toaster", "Duplicated toast message '$message'")
                return
            }

            else -> {
                messageList.add(message)
                handler.post {
                    Toast.makeText(
                        /* context = */ context,
                        /* text = */ message,
                        /* duration = */ if (isLong) {
                            Toast.LENGTH_LONG
                        } else {
                            Toast.LENGTH_SHORT
                        }
                    ).show()
                    messageList.remove(message)
                }
            }
        }
    }
}
