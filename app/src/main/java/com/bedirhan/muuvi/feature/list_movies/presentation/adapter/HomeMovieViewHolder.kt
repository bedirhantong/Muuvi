package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bedirhan.muuvi.common.Constants.MOVIE_IMAGE_POSTER_PATH
import com.bedirhan.muuvi.common.loadImage
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel

class HomeMovieViewHolder(
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
