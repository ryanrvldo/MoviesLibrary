package com.ryanrvldo.movieslibrary.core.data.network.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("status_code") val statusCode: Int
)
