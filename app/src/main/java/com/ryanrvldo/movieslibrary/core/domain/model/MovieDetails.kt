package com.ryanrvldo.movieslibrary.core.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<Genre>,
    val runtime: Int,
    val videos: List<Video>
)
