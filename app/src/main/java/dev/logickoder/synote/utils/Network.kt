package dev.logickoder.synote.utils

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun <T> HttpClient.call(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend HttpClient.() -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(this@call.apiCall())
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Napier.e(e.response.status.description)
            ResultWrapper.Failure(e)
        } catch (throwable: Throwable) {
            Napier.e(throwable.message.toString())
            ResultWrapper.Failure(throwable)
        }
    }
}