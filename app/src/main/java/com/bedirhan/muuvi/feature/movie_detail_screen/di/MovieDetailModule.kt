package com.bedirhan.muuvi.feature.movie_detail_screen.di

import com.bedirhan.muuvi.common.mapper.movies.MovieDetailMapper
import com.bedirhan.muuvi.feature.movie_detail_screen.data.remote.MovieDetailApiService
import com.bedirhan.muuvi.feature.movie_detail_screen.data.repository.MovieDetailRepositoryImpl
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.MovieDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDetailModule {
    @Provides
    @Singleton
    fun provideMovieDetailMapper(): MovieDetailMapper {
        return MovieDetailMapper()
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        movieDetailMapper: MovieDetailMapper
    ): MovieDetailRepository {
        return MovieDetailRepositoryImpl(
            movieDetailApiService, movieDetailMapper
        )
    }
}