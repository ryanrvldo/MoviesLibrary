package com.ryanrvldo.movieslibrary.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.movieslibrary.core.domain.repository.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    genreRepository: GenreRepository
) : ViewModel() {

    val uiState: StateFlow<GenreUiState> = genreRepository.getOfficialMovieGenreList()
        .map { result ->
            if (result.isSuccess) {
                GenreUiState.Success(result.getOrThrow())
            } else {
                GenreUiState.Error(result.exceptionOrNull()?.message.toString())
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GenreUiState.Loading,
        )

}
