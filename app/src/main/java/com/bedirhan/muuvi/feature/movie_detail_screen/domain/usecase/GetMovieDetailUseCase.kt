package com.bedirhan.muuvi.feature.movie_detail_screen.domain.usecase

import com.bedirhan.muuvi.feature.movie_detail_screen.domain.MovieDetailRepository
import com.bedirhan.muuvi.feature.movie_detail_screen.domain.uimodel.MovieDetailUiModel
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
){
    suspend operator fun invoke(movieId:Int):MovieDetailUiModel?{
        return movieDetailRepository.getMovieDetailById(movieId)
    }
}