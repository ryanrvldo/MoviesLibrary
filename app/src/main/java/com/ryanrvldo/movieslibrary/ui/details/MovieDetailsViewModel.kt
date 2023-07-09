package com.ryanrvldo.movieslibrary.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.movieslibrary.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val uiState: StateFlow<MovieDetailsUiState> = savedStateHandle.getStateFlow("id", 0)
        .flatMapConcat { id ->
            movieRepository.getMovieDetailsById(id)
                .zip(movieRepository.getMovieReviews(id)) { resultDetails, pagingData ->
                    if (resultDetails.isFailure) {
                        MovieDetailsUiState.Error(resultDetails.exceptionOrNull()?.message.toString())
                    } else {
                        MovieDetailsUiState.Success(resultDetails.getOrThrow(), pagingData)
                    }
                }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MovieDetailsUiState.Loading,
        )
}
