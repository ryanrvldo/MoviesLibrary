package com.ryanrvldo.movieslibrary.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.movieslibrary.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val uiState: StateFlow<MovieDetailsUiState> = savedStateHandle.getStateFlow("id", 0)
        .flatMapConcat { id ->
            movieRepository.getMovieDetailsById(id)
                .map { result ->
                    if (result.isSuccess) {
                        MovieDetailsUiState.Success(result.getOrThrow())
                    } else {
                        MovieDetailsUiState.Error(result.exceptionOrNull()?.message.toString())
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailsUiState.Loading,
        )
}
