package com.example.nourishlens.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint

object ImagePreprocessor {

    /**
     * Enhances image contrast and sharpens light-colored / faint dot-matrix text
     * on metallic, glossy, or low-contrast food packaging labels.
     */
    fun enhanceContrastForOcr(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(output)
        val paint = Paint()

        // Combine Grayscale and High Contrast Boost Matrix (Contrast 1.6x, Brightness +10)
        val contrast = 1.6f
        val brightness = -10f
        val cm = ColorMatrix(
            floatArrayOf(
                contrast * 0.299f, contrast * 0.587f, contrast * 0.114f, 0f, brightness,
                contrast * 0.299f, contrast * 0.587f, contrast * 0.114f, 0f, brightness,
                contrast * 0.299f, contrast * 0.587f, contrast * 0.114f, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
        )

        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(source, 0f, 0f, paint)

        return output
    }
}
