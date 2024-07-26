package com.bedirhan.muuvi.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showErrorSnackbar(message: String?) {
    Snackbar.make(this, message ?: "An error occurred", Snackbar.LENGTH_LONG).show()
}

fun View.showSuccessSnackbar(message: String?) {
    Snackbar.make(this, message ?: "Success", Snackbar.LENGTH_LONG).show()
}

fun View.showInfoSnackbar(message: String?) {
    Snackbar.make(this, message ?: "Information", Snackbar.LENGTH_LONG).show()
}