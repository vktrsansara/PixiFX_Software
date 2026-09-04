package com.vktrsansara.app.pixifx.domain.usecase

import com.vktrsansara.app.pixifx.domain.model.Effect
import com.vktrsansara.app.pixifx.domain.repository.IEffectRepository

class SetEffectUseCase(
    private val effectRepository: IEffectRepository
) {
    suspend operator fun invoke(ip: String, effect: Effect): Result<Unit> =
        effectRepository.setEffect(ip, effect)
}
