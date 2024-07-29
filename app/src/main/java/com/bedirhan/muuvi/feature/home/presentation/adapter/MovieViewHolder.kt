package com.bedirhan.muuvi.feature.home.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bedirhan.muuvi.utils.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.utils.extensions.loadImage
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class MovieViewHolder(
    private val binding: HomeMovieItemBinding,
    private val onClickMovie: ((movieId: Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) = binding.apply {
        itemView.setOnClickListener {
            movie.id?.let { it1 ->
                onClickMovie?.invoke(it1)
            }
        }
        textViewTitle.text = movie.title
        imageViewMovie.loadImage(MOVIE_IMAGE_POSTER_PATH + movie.posterPath)
    }
}
