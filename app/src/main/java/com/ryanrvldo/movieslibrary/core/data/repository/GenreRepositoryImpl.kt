package com.ryanrvldo.movieslibrary.core.data.repository

import com.google.gson.GsonBuilder
import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import com.ryanrvldo.movieslibrary.core.domain.repository.GenreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GenreRepositoryImpl @Inject constructor() : GenreRepository {

    override fun getOfficialMovieGenreList(): Flow<List<Genre>> = flow {
        val json =
            """[{"id":28,"name":"Action"},{"id":12,"name":"Adventure"},{"id":16,"name":"Animation"},{"id":35,"name":"Comedy"},{"id":80,"name":"Crime"},{"id":99,"name":"Documentary"},{"id":18,"name":"Drama"},{"id":10751,"name":"Family"},{"id":14,"name":"Fantasy"},{"id":36,"name":"History"},{"id":27,"name":"Horror"},{"id":10402,"name":"Music"},{"id":9648,"name":"Mystery"},{"id":10749,"name":"Romance"},{"id":878,"name":"Science Fiction"},{"id":10770,"name":"TV Movie"},{"id":53,"name":"Thriller"},{"id":10752,"name":"War"},{"id":37,"name":"Western"}]"""
        val genres = GsonBuilder()
            .create()
            .fromJson(json, Array<Genre>::class.java)
        delay(2000)
        emit(genres.toList())
    }

}