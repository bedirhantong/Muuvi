package com.bedirhan.muuvi.utils.extensions

import android.content.Context
import android.widget.Button
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.bedirhan.muuvi.R

fun Button.updateState(isEnabled: Boolean, context: Context) {
    this.isEnabled = isEnabled
    backgroundTintList = ColorStateList.valueOf(
        if (isEnabled) ContextCompat.getColor(context, R.color.button_enabled)
        else ContextCompat.getColor(context, R.color.button_disabled)
    )
}