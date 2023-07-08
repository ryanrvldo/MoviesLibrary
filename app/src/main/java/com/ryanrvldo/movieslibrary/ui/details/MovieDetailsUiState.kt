package com.ryanrvldo.movieslibrary.ui.details

import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails

sealed interface MovieDetailsUiState {
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState
    data class Error(val message: String) : MovieDetailsUiState
    object Loading : MovieDetailsUiState
}
