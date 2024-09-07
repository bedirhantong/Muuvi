package com.bedirhan.muuvi.feature.authentication.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthScreenUiState(
    var email: String = "",
    var password: String = "",
): Parcelable

// auth ui model yap ama burası state olmadı
// ui stateleri araştır
