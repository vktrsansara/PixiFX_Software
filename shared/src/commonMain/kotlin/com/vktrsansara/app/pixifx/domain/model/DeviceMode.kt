package com.vktrsansara.app.pixifx.domain.model

/**
 * Operating modes of PixiFX ESP8266 controller matching firmware definitions:
 * - MODE_SETUP (0): Open AP PixiSetup_XXXXXX for initial configuration
 * - MODE_MASTER (1): STA connection to router + active AP for slaves
 * - MODE_CLIENT (2): STA connection to master controller
 * - MODE_HOST (3): Autonomous AP mode without router connection
 */
enum class DeviceMode(val code: Int, val displayName: String) {
    SETUP(0, "SETUP"),
    MASTER(1, "MASTER"),
    CLIENT(2, "CLIENT"),
    HOST(3, "HOST"),
    UNKNOWN(-1, "UNKNOWN");

    companion object {
        fun fromCode(code: Int): DeviceMode =
            entries.find { it.code == code } ?: UNKNOWN
    }
}
