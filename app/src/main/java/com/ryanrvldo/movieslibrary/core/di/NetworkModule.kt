package com.ryanrvldo.movieslibrary.core.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ryanrvldo.movieslibrary.BuildConfig
import com.ryanrvldo.movieslibrary.core.data.network.config.AuthInterceptor
import com.ryanrvldo.movieslibrary.core.data.network.service.GenreService
import com.ryanrvldo.movieslibrary.core.data.network.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder()
        .create()

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
            )
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun providesGenreService(retrofit: Retrofit): GenreService = retrofit.create()

    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService = retrofit.create()
}
