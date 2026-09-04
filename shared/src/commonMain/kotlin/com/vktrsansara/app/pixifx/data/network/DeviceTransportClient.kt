package com.vktrsansara.app.pixifx.data.network

import com.vktrsansara.app.pixifx.data.network.dto.DeviceDto
import com.vktrsansara.app.pixifx.data.network.dto.DeviceSettingsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DeviceTransportClient(
    private val httpClient: HttpClient
) {
    private fun normalizeUrl(ipOrHost: String, path: String = ""): String {
        val base = when {
            ipOrHost.startsWith("http://") || ipOrHost.startsWith("https://") -> ipOrHost.trimEnd('/')
            else -> "http://${ipOrHost.trim().trimEnd('/')}"
        }
        val cleanPath = if (path.startsWith("/")) path else "/$path"
        return "$base$cleanPath"
    }

    /**
     * Queries the device root endpoint to get status and device identity.
     */
    suspend fun getDeviceInfo(ipOrHost: String, timeoutMillis: Long = 1000): Result<DeviceDto> = runCatching {
        val url = normalizeUrl(ipOrHost, "/")
        val response = httpClient.get(url) {
            timeout {
                connectTimeoutMillis = timeoutMillis
                requestTimeoutMillis = timeoutMillis
                socketTimeoutMillis = timeoutMillis
            }
        }
        response.body<DeviceDto>()
    }

    /**
     * Fetches complete device configuration from ESP8266 (/api/settings).
     */
    suspend fun getSettings(ipOrHost: String, timeoutMillis: Long = 3000): Result<DeviceSettingsDto> = runCatching {
        val url = normalizeUrl(ipOrHost, "/api/settings")
        val response = httpClient.get(url) {
            timeout {
                connectTimeoutMillis = timeoutMillis
                requestTimeoutMillis = timeoutMillis
            }
        }
        response.body<DeviceSettingsDto>()
    }

    /**
     * Sends new settings to ESP8266 and triggers reboot.
     */
    suspend fun saveSettings(ipOrHost: String, settings: DeviceSettingsDto): Result<Unit> = runCatching {
        val url = normalizeUrl(ipOrHost, "/api/settings")
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(settings)
        }
    }

    suspend fun setEffect(ip: String, effectId: Int, speed: Int, brightness: Int): Result<Unit> = runCatching {
        val url = normalizeUrl(ip, "/api/effect")
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("id" to effectId, "speed" to speed, "brightness" to brightness))
        }
    }

    suspend fun setBrightness(ip: String, brightness: Int): Result<Unit> = runCatching {
        val url = normalizeUrl(ip, "/api/brightness")
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("brightness" to brightness))
        }
    }

    suspend fun setColor(ip: String, r: Int, g: Int, b: Int): Result<Unit> = runCatching {
        val url = normalizeUrl(ip, "/api/color")
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(mapOf("r" to r, "g" to g, "b" to b))
        }
    }
}
