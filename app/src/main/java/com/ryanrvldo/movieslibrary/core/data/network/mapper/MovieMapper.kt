package com.ryanrvldo.movieslibrary.core.data.network.mapper

import com.ryanrvldo.movieslibrary.core.data.network.response.GenreResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieDetailsResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.VideoResponse
import com.ryanrvldo.movieslibrary.core.domain.model.Movie
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.core.domain.model.Video

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
    runtime = runtime,
    videos = videos.results
        .filter { it.site.equals("Youtube", true) && it.type.equals("Trailer", true) }
        .map(VideoResponse::mapToDomain)
)

fun VideoResponse.mapToDomain() = Video(
    name = name,
    key = key,
    site = site,
    type = type
)
