package com.ryanrvldo.movieslibrary.core.data.repository

import com.google.gson.Gson
import com.ryanrvldo.movieslibrary.core.data.network.mapper.mapToDomain
import com.ryanrvldo.movieslibrary.core.data.network.service.GenreService
import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import com.ryanrvldo.movieslibrary.core.domain.repository.GenreRepository
import com.ryanrvldo.movieslibrary.core.util.handleApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreService: GenreService, private val gson: Gson
) : GenreRepository {

    override fun getOfficialMovieGenreList(): Flow<Result<List<Genre>>> = handleApiResponse(gson) {
        val response = genreService.getOfficialMovieGenreList()
        response.genres.map { it.mapToDomain() }
    }

}
