package com.ryanrvldo.movieslibrary.ui.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryanrvldo.movieslibrary.core.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val uiState: StateFlow<MoviesUiState> = savedStateHandle.getStateFlow("genreId", 28)
        .flatMapConcat { id ->
            movieRepository.getMoviesByGenreId(id)
                .map { MoviesUiState.Success(it) as MoviesUiState }
                .catch {
                    Timber.e(it)
                    emit(MoviesUiState.Error(it.message!!))
                }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MoviesUiState.Loading,
        )

}
