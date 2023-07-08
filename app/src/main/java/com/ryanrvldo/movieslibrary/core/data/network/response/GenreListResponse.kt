package com.ryanrvldo.movieslibrary.core.data.network.response

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres") val genres: List<GenreResponse>
)
