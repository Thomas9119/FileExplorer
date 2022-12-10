package com.example.fileexplorer.util

import android.content.res.Resources
import kotlin.math.roundToInt

object ScreenUtils {
    fun convertDpToPixel(var0: Float): Int {
        return (var0 * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / 160.0f)).roundToInt()
    }
}