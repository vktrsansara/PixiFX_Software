package com.vktrsansara.app.pixifx.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(
    val title: String,
    val icon: ImageVector
) {
    DEVICES("Устройства", Icons.Default.Devices),
    REMOTE("Пульт", Icons.Default.Tune),
    CONSTRUCTOR("Конструктор", Icons.Default.AutoAwesome),
    SETTINGS("Настройки", Icons.Default.Settings)
}
