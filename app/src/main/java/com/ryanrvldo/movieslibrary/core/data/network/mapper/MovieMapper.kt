package com.ryanrvldo.movieslibrary.core.data.network.mapper

import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.domain.model.Movie

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
