package com.bedirhan.muuvi.core

import com.bedirhan.muuvi.feature.list_movies.data.remote.MovieApiService
import com.bedirhan.muuvi.feature.movie_detail_screen.data.remote.MovieDetailApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDetailServices(retrofit: Retrofit):MovieDetailApiService{
        return retrofit.create(MovieDetailApiService::class.java)
    }

    companion object {
        const val MOVIE_BASE_URL = "https://api.themoviedb.org/3/"
    }
}