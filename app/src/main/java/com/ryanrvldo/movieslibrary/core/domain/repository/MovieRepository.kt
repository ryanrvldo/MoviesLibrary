package com.ryanrvldo.movieslibrary.core.domain.repository

import androidx.paging.PagingData
import com.ryanrvldo.movieslibrary.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviesByGenreId(genreId: Int): Flow<PagingData<Movie>>

}
