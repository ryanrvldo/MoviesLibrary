package com.ryanrvldo.movieslibrary.ui.movies

import androidx.paging.PagingData
import com.ryanrvldo.movieslibrary.core.domain.model.Movie

sealed interface MoviesUiState {
    data class Success(val moviePagingData: PagingData<Movie>) : MoviesUiState
    data class Error(val message: String) : MoviesUiState
    object Loading : MoviesUiState
}
