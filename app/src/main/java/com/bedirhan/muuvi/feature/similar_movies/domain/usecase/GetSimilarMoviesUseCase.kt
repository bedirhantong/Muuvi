package com.bedirhan.muuvi.feature.similar_movies.domain.usecase

import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class GetSimilarMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieListUiModel?>> = flow {
        try {
            emit(Resource.Loading())
            val searchedMovies = movieRepository.getSimilarMovies(movieId)
            emit(Resource.Success(data = searchedMovies))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }
}