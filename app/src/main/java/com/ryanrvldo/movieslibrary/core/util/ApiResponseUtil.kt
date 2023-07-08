package com.ryanrvldo.movieslibrary.core.util

import com.google.gson.Gson
import com.ryanrvldo.movieslibrary.core.data.network.response.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

fun <T> handleApiResponse(gson: Gson, apiCall: suspend () -> T) = flow {
    emit(Result.success(apiCall.invoke()))
}.catch { exception ->
    Timber.e(exception)
    when (exception) {
        is IOException -> {
            emit(Result.failure(RuntimeException("No internet connection. Make sure you have stable network connection.")))
        }

        is HttpException -> {
            val errorResponse = gson.fromJson(
                exception.response()?.errorBody()?.charStream(),
                ErrorResponse::class.java
            )
            emit(Result.failure(RuntimeException("${errorResponse.statusCode} - ${errorResponse.statusMessage}")))
        }

        else -> {
            emit(Result.failure(exception))
        }
    }
}.flowOn(Dispatchers.IO)
