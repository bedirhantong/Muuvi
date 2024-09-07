package com.bedirhan.muuvi.utils.extensions

import android.util.Log
import com.bedirhan.muuvi.BuildConfig

const val LOG_TAG = "muuvi_logger"
fun logE(message: String,tag: String= LOG_TAG) {
    log { Log.e(tag, message) }
}

private inline fun log(func: () -> Int) {
    if (BuildConfig.DEBUG) {
        func()
    }
}
