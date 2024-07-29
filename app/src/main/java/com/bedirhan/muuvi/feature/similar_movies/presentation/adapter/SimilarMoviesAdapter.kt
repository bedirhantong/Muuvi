package com.bedirhan.muuvi.feature.similar_movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bedirhan.muuvi.databinding.SimilarMovieItemBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class SimilarMoviesAdapter (
    private val onClickMovie: ((movieId: Int) -> Unit)?
) :
    ListAdapter<MovieUiModel, SimilarMovieViewHolder>(SimilarMovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : SimilarMovieViewHolder {
        return SimilarMovieViewHolder(
            SimilarMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickMovie
        )
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}