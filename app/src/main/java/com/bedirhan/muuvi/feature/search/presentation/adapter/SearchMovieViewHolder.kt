package com.bedirhan.muuvi.feature.search.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bedirhan.muuvi.databinding.SearchMovieItemBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieUiModel
import com.bedirhan.muuvi.utils.Constants
import com.bedirhan.muuvi.utils.extensions.loadImage

class SearchMovieViewHolder (
    private val binding: SearchMovieItemBinding,
    private val onClickMovie: ((movieId: Int) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) = binding.apply {
//        itemView.setOnClickListener {
//
//        }
        root.setOnClickListener{
            movie.id?.let { it1 ->
                onClickMovie?.invoke(it1)
            }
        }
        tvOverview.text = movie.overview
        tvReleaseDate.text = movie.releaseDate
        tvVoteCount.text = movie.voteCount.toString()
        tvTitle.text = movie.title
        ivPosterImage.loadImage(Constants.MOVIE_IMAGE_POSTER_PATH + movie.posterPath)
        tvPopularity.text = movie.popularity.toString()

    }
}
