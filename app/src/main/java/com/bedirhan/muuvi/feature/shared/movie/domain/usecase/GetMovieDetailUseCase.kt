package com.bedirhan.muuvi.feature.shared.movie.domain.usecase

import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Resource<MovieUiModel?>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = movieDetailRepository.getMovieDetailById(movieId)
            emit(Resource.Success(data = movieDetail))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }
}
