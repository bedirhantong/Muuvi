package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bedirhan.muuvi.common.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.common.loadImage
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel

class HomeMovieViewHolder(
    private val binding: HomeMovieItemBinding
) :RecyclerView.ViewHolder(binding.root){
    fun bind(movie: MovieUiModel) {
        binding.apply {
            textViewTitle.text = movie.title
            imageViewMovie.loadImage(MOVIE_IMAGE_POSTER_PATH+movie.posterPath)
        }
    }
}