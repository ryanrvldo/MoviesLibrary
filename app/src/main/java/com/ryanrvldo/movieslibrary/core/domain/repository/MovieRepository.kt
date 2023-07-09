package com.ryanrvldo.movieslibrary.core.domain.repository

import androidx.paging.PagingData
import com.ryanrvldo.movieslibrary.core.domain.model.Movie
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.core.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByGenreId(genreId: Int): Flow<PagingData<Movie>>

    fun getMovieDetailsById(id: Int): Flow<Result<MovieDetails>>

    fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>

}
