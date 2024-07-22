package com.bedirhan.muuvi.feature.movie_detail_screen.domain.usecase

import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.MovieDetailRepository
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<MovieDetailUiModel?> {
        return try {
            val movieDetail = movieDetailRepository.getMovieDetailById(movieId)
            Resource.Success(movieDetail)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
