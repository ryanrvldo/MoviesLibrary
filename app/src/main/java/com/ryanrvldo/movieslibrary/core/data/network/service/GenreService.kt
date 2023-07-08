package com.ryanrvldo.movieslibrary.core.data.network.service

import com.ryanrvldo.movieslibrary.core.data.network.response.GenreListResponse
import retrofit2.http.GET

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getOfficialMovieGenreList(): GenreListResponse
}
