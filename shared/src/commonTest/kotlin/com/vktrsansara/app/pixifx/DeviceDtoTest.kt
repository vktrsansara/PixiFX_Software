package com.vktrsansara.app.pixifx

import com.vktrsansara.app.pixifx.data.network.dto.DeviceDto
import com.vktrsansara.app.pixifx.domain.model.DeviceMode
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeviceDtoTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun testDeviceDtoDeserialization() {
        val jsonString = """
            {
              "device": "pixifx-device-esp8266",
              "device_id": "Pixi_A1B2C3D4",
              "mode": 0,
              "is_setup": true,
              "ip": "10.10.1.1"
            }
        """.trimIndent()

        val dto = json.decodeFromString<DeviceDto>(jsonString)
        assertEquals("pixifx-device-esp8266", dto.device)
        assertEquals("Pixi_A1B2C3D4", dto.deviceId)
        assertEquals(0, dto.mode)
        assertTrue(dto.isSetup)
        assertEquals("10.10.1.1", dto.ip)

        val domain = dto.toDomain(fallbackIp = "10.10.1.1")
        assertEquals("Pixi_A1B2C3D4", domain.id)
        assertEquals("10.10.1.1", domain.ip)
        assertEquals(DeviceMode.SETUP, domain.mode)
        assertTrue(domain.isSetup)
    }
}
