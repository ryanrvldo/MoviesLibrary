package com.ryanrvldo.movieslibrary.core.data.network.service

import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ): PagingResponse<MovieResponse>

}
