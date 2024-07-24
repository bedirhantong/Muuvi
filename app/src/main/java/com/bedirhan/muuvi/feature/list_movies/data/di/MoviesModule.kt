package com.bedirhan.muuvi.feature.list_movies.data.di

import com.bedirhan.muuvi.common.mapper.movies.MovieMapper
import com.bedirhan.muuvi.feature.list_movies.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.list_movies.data.repository.MovieRepositoryImpl
import com.bedirhan.muuvi.feature.list_movies.domain.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {
    @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper {
        return MovieMapper()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: MovieApiService,
        movieMapper: MovieMapper
    ): MovieRepository {
        return MovieRepositoryImpl(apiService, movieMapper)
    }
}