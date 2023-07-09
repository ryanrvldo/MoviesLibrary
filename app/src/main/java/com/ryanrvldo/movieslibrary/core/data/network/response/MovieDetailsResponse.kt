package com.ryanrvldo.movieslibrary.core.data.network.response

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genres") val genres: List<GenreResponse>,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("videos") val videos: VideoListResponse
)
