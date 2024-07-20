package com.bedirhan.muuvi.feature.list_movies.data.di

import com.bedirhan.muuvi.common.mapper.movies.MovieMapper
import com.bedirhan.muuvi.feature.list_movies.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.list_movies.data.repository.ListMovieRepositoryImpl
import com.bedirhan.muuvi.feature.list_movies.domain.ListMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ListMoviesModule {
    @Provides
    @Singleton
    fun provideNewsMapper(): MovieMapper {
        return MovieMapper()
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        apiService: MovieApiService,
        newsMapper: MovieMapper
    ): ListMovieRepository {
        return ListMovieRepositoryImpl(apiService, newsMapper)
    }
}