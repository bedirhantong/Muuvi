package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.MovieUiModel

class HomeFeedMovieRecyclerAdapter(
    private val onClickMovie: ((movieId: Int) -> Unit)?
) :
    ListAdapter<MovieUiModel, HomeMovieViewHolder>(MovieDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : HomeMovieViewHolder {
        return HomeMovieViewHolder(
            HomeMovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickMovie
        )
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}