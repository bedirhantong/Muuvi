package com.bedirhan.muuvi.common

import android.widget.ImageView
import com.bedirhan.muuvi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.placeholder_centered)
        .error(R.drawable.placeholder_centered)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
