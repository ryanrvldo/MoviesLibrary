package com.ryanrvldo.movieslibrary.core.domain.repository

import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface GenreRepository {

   fun getOfficialMovieGenreList(): Flow<Result<List<Genre>>>

}
