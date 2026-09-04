package com.vktrsansara.app.pixifx.domain.model

data class Effect(
    val id: Int,
    val name: String,
    val speed: Int = 128,
    val brightness: Int = 255,
    val primaryColor: RgbColor = RgbColor(255, 0, 0),
    val secondaryColor: RgbColor = RgbColor(0, 0, 255)
)
