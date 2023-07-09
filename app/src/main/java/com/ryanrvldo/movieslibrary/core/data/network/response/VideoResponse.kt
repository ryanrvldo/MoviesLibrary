package com.ryanrvldo.movieslibrary.core.data.network.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String
)
