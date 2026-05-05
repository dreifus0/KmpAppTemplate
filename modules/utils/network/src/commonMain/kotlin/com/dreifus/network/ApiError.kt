package com.dreifus.network

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

sealed interface ApiError {
    data object Network : ApiError
    data class Http(val code: Int) : ApiError
    data class Serialization(val cause: Throwable) : ApiError
    data class Unknown(val cause: Throwable) : ApiError
}

fun Throwable.toApiError(): ApiError = when (this) {
    is CancellationException -> throw this
    is ResponseException -> ApiError.Http(response.status.value)
    is ConnectTimeoutException,
    is SocketTimeoutException,
    is HttpRequestTimeoutException -> ApiError.Network
    is SerializationException -> ApiError.Serialization(this)
    else -> ApiError.Unknown(this)
}
