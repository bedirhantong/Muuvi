package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel

class MovieRecyclerAdapter(
    private val onClickMovie: ((movieId: Int) -> Unit)?
) :
    ListAdapter<MovieUiModel, MovieViewHolder>(MovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : MovieViewHolder {
        return MovieViewHolder(
            HomeMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickMovie
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}