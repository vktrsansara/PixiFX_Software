package com.vktrsansara.app.pixifx.domain.repository

import com.vktrsansara.app.pixifx.domain.model.Effect
import com.vktrsansara.app.pixifx.domain.model.RgbColor

interface IEffectRepository {
    suspend fun setEffect(ip: String, effect: Effect): Result<Unit>
    suspend fun setBrightness(ip: String, brightness: Int): Result<Unit>
    suspend fun setColor(ip: String, color: RgbColor): Result<Unit>
}
