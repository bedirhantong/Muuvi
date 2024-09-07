package com.bedirhan.muuvi.feature.search.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bedirhan.muuvi.databinding.SearchMovieItemBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class SearchMoviesAdapter (
    private val onClickMovie: ((movieId: Int) -> Unit)?
) :
    ListAdapter<MovieUiModel, SearchMovieViewHolder>(SearchMovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : SearchMovieViewHolder {
        return SearchMovieViewHolder(
            SearchMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickMovie
        )
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}