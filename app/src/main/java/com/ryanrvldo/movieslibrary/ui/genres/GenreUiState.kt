package com.ryanrvldo.movieslibrary.ui.genres

import com.ryanrvldo.movieslibrary.core.domain.model.Genre

sealed interface GenreUiState {
    data class Success(val news: List<Genre>) : GenreUiState
    data class Error(val message: String) : GenreUiState
    object Loading : GenreUiState
}