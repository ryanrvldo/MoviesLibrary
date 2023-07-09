package com.ryanrvldo.movieslibrary.core.data.repository

import androidx.paging.map
import com.google.gson.Gson
import com.ryanrvldo.movieslibrary.core.data.network.mapper.mapToDomain
import com.ryanrvldo.movieslibrary.core.data.network.paging.buildPager
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.ReviewResponse
import com.ryanrvldo.movieslibrary.core.data.network.service.MovieService
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.core.domain.repository.MovieRepository
import com.ryanrvldo.movieslibrary.core.util.handleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val gson: Gson,
    private val movieService: MovieService
) : MovieRepository {

    override fun getMoviesByGenreId(genreId: Int) = buildPager { page ->
        movieService.getMoviesByGenreId(page, genreId)
    }.flow
        .flowOn(Dispatchers.IO)
        .map { pagingData -> pagingData.map(MovieResponse::mapToDomain) }

    override fun getMovieDetailsById(id: Int): Flow<Result<MovieDetails>> =
        handleApiResponse(gson) {
            val response = movieService.getMovieDetailsById(id)
            response.mapToDomain()
        }

    override fun getMovieReviews(movieId: Int) = buildPager { page ->
        movieService.getMovieReviews(movieId, page)
    }.flow
        .flowOn(Dispatchers.IO)
        .map { pagingData -> pagingData.map(ReviewResponse::mapToDomain) }

}
