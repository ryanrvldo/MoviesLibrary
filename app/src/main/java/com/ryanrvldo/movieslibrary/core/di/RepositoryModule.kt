package com.ryanrvldo.movieslibrary.core.di

import com.ryanrvldo.movieslibrary.core.data.repository.GenreRepositoryImpl
import com.ryanrvldo.movieslibrary.core.data.repository.MovieRepositoryImpl
import com.ryanrvldo.movieslibrary.core.domain.repository.GenreRepository
import com.ryanrvldo.movieslibrary.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsGenreRepository(repositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    @Singleton
    fun bindsMovieRepository(repositoryImpl: MovieRepositoryImpl): MovieRepository

}
