package com.bedirhan.muuvi.feature.home.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieUiModel>() {
    override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return when{
            oldItem.title != newItem.title -> {
                false
            }
            oldItem.id != newItem.id -> {
                false
            }
            oldItem.genreIds != newItem.genreIds -> {
                false
            }
            oldItem.video != newItem.video -> {
                false
            }
            oldItem.adult != newItem.adult -> {
                false
            }
            oldItem.overview != newItem.overview -> {
                false
            }
            oldItem.posterPath != newItem.posterPath -> {
                false
            }
            else -> true
        }
    }
}