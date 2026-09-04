package com.vktrsansara.app.pixifx.domain.model

/**
 * Pure Kotlin representation of an RGB color (0..255 per channel).
 */
data class RgbColor(
    val r: Int = 255,
    val g: Int = 255,
    val b: Int = 255
) {
    init {
        require(r in 0..255) { "Red must be between 0 and 255, was $r" }
        require(g in 0..255) { "Green must be between 0 and 255, was $g" }
        require(b in 0..255) { "Blue must be between 0 and 255, was $b" }
    }

    companion object {
        val RED = RgbColor(255, 0, 0)
        val GREEN = RgbColor(0, 255, 0)
        val BLUE = RgbColor(0, 0, 255)
        val WHITE = RgbColor(255, 255, 255)
        val BLACK = RgbColor(0, 0, 0)
        val CYAN = RgbColor(0, 255, 255)
        val MAGENTA = RgbColor(255, 0, 255)
        val YELLOW = RgbColor(255, 255, 0)
    }
}

/**
 * Pure Kotlin representation of an HSV color.
 */
data class HsvColor(
    val hue: Float = 0f,        // 0..360
    val saturation: Float = 1f, // 0..1
    val value: Float = 1f       // 0..1
)
