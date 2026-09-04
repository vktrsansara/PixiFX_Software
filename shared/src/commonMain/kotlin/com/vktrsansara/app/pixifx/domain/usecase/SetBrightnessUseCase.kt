package com.vktrsansara.app.pixifx.domain.usecase

import com.vktrsansara.app.pixifx.domain.repository.IEffectRepository

class SetBrightnessUseCase(
    private val effectRepository: IEffectRepository
) {
    suspend operator fun invoke(ip: String, brightness: Int): Result<Unit> =
        effectRepository.setBrightness(ip, brightness)
}
