package com.ryanrvldo.movieslibrary.core.data.network.response

import com.google.gson.annotations.SerializedName

data class VideoListResponse(
    @SerializedName("results") val results: List<VideoResponse>
)
