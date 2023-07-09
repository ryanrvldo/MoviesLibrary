package com.ryanrvldo.movieslibrary.core.data.network.service

import com.ryanrvldo.movieslibrary.core.data.network.response.MovieDetailsResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.MovieResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.PagingResponse
import com.ryanrvldo.movieslibrary.core.data.network.response.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ): PagingResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "videos"
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): PagingResponse<ReviewResponse>

}
