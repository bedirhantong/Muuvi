package com.bedirhan.muuvi.feature.shared.movie.domain.usecase

import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUpcomingMoviesUseCase@Inject constructor(
    private val listMovieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<MovieListUiModel?>> = flow {
        try {
            emit(Resource.Loading())
            val movies = listMovieRepository.getUpcomingMovies()
            emit(Resource.Success(data = movies))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }
}