package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bedirhan.muuvi.databinding.MovieListItemBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class ListMoviesAdapter (
    private val onClickMovie: ((movieId: Int) -> Unit)?
) :
    ListAdapter<MovieUiModel, ListMovieViewHolder>(ListMovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ListMovieViewHolder {
        return ListMovieViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickMovie
        )
    }

    override fun onBindViewHolder(holder: ListMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}