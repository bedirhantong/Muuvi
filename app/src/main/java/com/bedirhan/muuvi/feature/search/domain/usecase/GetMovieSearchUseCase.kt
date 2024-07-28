package com.bedirhan.muuvi.feature.search.domain.usecase

import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.shared.movie.domain.MovieRepository
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel
import com.bedirhan.muuvi.utils.extensions.logE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieSearchUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query:String): Flow<Resource<MovieListUiModel?>> = flow {
        try {
            emit(Resource.Loading())
            val searchedMovies = movieRepository.searchMovieByQuery(query)
            emit(Resource.Success(data = searchedMovies))
        }catch (e: HttpException) {
            emit(Resource.Error(message = e.message))
        } catch (e: IOException) {
            emit(Resource.Error(message = e.localizedMessage))
        }
    }
}