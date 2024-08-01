package com.bedirhan.muuvi.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

fun <T> collectPageState(
    viewLifecycleOwner: LifecycleOwner,
    pageState: StateFlow<T>,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        pageState.collect { state ->
            block(state)
        }
    }
}

