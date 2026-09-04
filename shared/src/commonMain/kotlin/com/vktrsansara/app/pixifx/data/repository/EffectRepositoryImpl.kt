package com.vktrsansara.app.pixifx.data.repository

import com.vktrsansara.app.pixifx.data.network.DeviceTransportClient
import com.vktrsansara.app.pixifx.domain.model.Effect
import com.vktrsansara.app.pixifx.domain.model.RgbColor
import com.vktrsansara.app.pixifx.domain.repository.IEffectRepository

class EffectRepositoryImpl(
    private val transportClient: DeviceTransportClient
) : IEffectRepository {

    override suspend fun setEffect(ip: String, effect: Effect): Result<Unit> =
        transportClient.setEffect(ip, effect.id, effect.speed, effect.brightness)

    override suspend fun setBrightness(ip: String, brightness: Int): Result<Unit> =
        transportClient.setBrightness(ip, brightness)

    override suspend fun setColor(ip: String, color: RgbColor): Result<Unit> =
        transportClient.setColor(ip, color.r, color.g, color.b)
}
