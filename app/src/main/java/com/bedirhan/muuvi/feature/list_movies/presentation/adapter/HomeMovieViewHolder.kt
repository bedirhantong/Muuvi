package com.bedirhan.muuvi.feature.list_movies.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bedirhan.muuvi.common.loadImage
import com.bedirhan.muuvi.databinding.HomeMovieItemBinding
import com.bedirhan.muuvi.feature.list_movies.domain.uimodel.HomeMovieUiModel

class HomeMovieViewHolder(
    private val binding: HomeMovieItemBinding
) :RecyclerView.ViewHolder(binding.root){
    private val posterBaseUrl = "https://image.tmdb.org/t/p/w500/"
    fun bind(news: HomeMovieUiModel) {
        binding.apply {
            textViewTitle.text = news.title
//            textViewDescription.text = news.overview
//            textViewReleaseDate.text = news.releaseDate
//            textViewPopularity.text = news.popularity.toString()
            imageViewMovie.loadImage(posterBaseUrl+news.posterPath)
        }
    }
}