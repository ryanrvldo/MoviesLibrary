package com.ryanrvldo.movieslibrary.ui.details

import androidx.paging.PagingData
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.core.domain.model.Review

sealed interface MovieDetailsUiState {
    data class Success(
        val movieDetails: MovieDetails,
        val reviewPagingData: PagingData<Review>
    ) : MovieDetailsUiState

    data class Error(val message: String) : MovieDetailsUiState
    object Loading : MovieDetailsUiState
}
