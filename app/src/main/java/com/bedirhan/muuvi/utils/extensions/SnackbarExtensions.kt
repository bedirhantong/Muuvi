package com.bedirhan.muuvi.utils.extensions

import android.view.View
import com.bedirhan.muuvi.R
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnackbar(message: String?) {
    val finalMessage = message ?: context.getString(R.string.extension_snackbar_error)
    Snackbar.make(this, finalMessage, Snackbar.LENGTH_LONG).show()
}

fun View.showSuccessSnackbar(message: String?) {
    val finalMessage = message ?: context.getString(R.string.extension_snackbar_success)
    Snackbar.make(this, finalMessage, Snackbar.LENGTH_LONG).show()
}

fun View.showInfoSnackbar(message: String?) {
    val finalMessage = message ?: context.getString(R.string.extension_snackbar_info)
    Snackbar.make(this, finalMessage, Snackbar.LENGTH_LONG).show()
}
