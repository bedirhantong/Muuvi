package com.bedirhan.muuvi.utils.extensions

import com.google.android.material.textfield.TextInputLayout
import android.graphics.Color
import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.bedirhan.muuvi.R

fun TextInputLayout.clearError() {
    if (isErrorEnabled) {
        isErrorEnabled = false
    }
}

fun TextInputLayout.setSuccessIcon() {
    setStartIconDrawable(R.drawable.check_circle)
    setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
}

fun TextInputLayout.setErrorC(message: String?) {
    isErrorEnabled = true
    error = message
}

fun EditText.addTextChangedListener(onTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}