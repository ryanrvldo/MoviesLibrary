package com.ryanrvldo.movieslibrary.core.data.network.mapper

import com.ryanrvldo.movieslibrary.core.data.network.response.GenreResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieDetailsResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.domain.model.Movie
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails

fun MovieResponse.mapToDomain() = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun MovieDetailsResponse.mapToDomain() = MovieDetails(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount,
    genres = genres.map(GenreResponse::mapToDomain),
    runtime = runtime
)
