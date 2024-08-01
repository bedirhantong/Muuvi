package com.bedirhan.muuvi.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import com.bedirhan.muuvi.common.Resource
import com.bedirhan.muuvi.databinding.LayoutShimmerHomeMoviesBinding
import com.bedirhan.muuvi.feature.shared.movie.domain.uimodel.MovieListUiModel

fun Fragment.handleResource(
    resource: Resource<MovieListUiModel>,
    shimmerLayout: View,
    shimmerEffectLayout : LayoutShimmerHomeMoviesBinding,
    recyclerView: View,
    onSuccess: (data: MovieListUiModel) -> Unit,
) {
    when (resource) {
        is Resource.Loading -> {
            shimmerLayout.show()
            recyclerView.hide()
            showShimmerEffect(shimmerEffectLayout,true)
        }
        is Resource.Success -> {
            shimmerLayout.hide()
            recyclerView.show()
            showShimmerEffect(shimmerEffectLayout,false)
            resource.data?.let { data -> onSuccess(data) }
        }
        is Resource.Error -> {
            shimmerLayout.hide()
            showShimmerEffect(shimmerEffectLayout,false)
            view?.let { rootView ->
                Snackbar.make(rootView, resource.message ?: "An error occurred", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}

fun showShimmerEffect(shimmerLayout: LayoutShimmerHomeMoviesBinding,show: Boolean) {
    if (show) shimmerLayout.root.show() else shimmerLayout.root.hide()

}
