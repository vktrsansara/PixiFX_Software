package com.vktrsansara.app.pixifx.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lan
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.model.DeviceMode
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBackground
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCard
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCardBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCyan
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightGreen
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightOrange
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSecondary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSurfaceVariant
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextSecondary

@Composable
fun DeviceCard(
    device: Device,
    isSelected: Boolean = false,
    onSelectChanged: (Boolean) -> Unit = {},
    onClickInfo: (Device) -> Unit = {},
    onClickDownload: (Device) -> Unit = {},
    onClickSettings: (Device) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = TokyoNightCard
        ),
        border = BorderStroke(
            width = if (isSelected) 1.5.dp else 1.dp,
            color = if (isSelected) TokyoNightPrimary else TokyoNightCardBorder
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. Верхняя строка: Иконка чипа + Имя устройства + Бейдж режима
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Memory,
                        contentDescription = null,
                        tint = TokyoNightPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = device.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TokyoNightTextPrimary
                        )
                    )
                }

                DeviceModeBadge(mode = device.mode, isSetup = device.isSetup)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 2. Вторая строка: Иконка сети + IP-адрес
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lan,
                    contentDescription = null,
                    tint = TokyoNightTextSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = device.ip,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TokyoNightPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Третья строка: Чекбокс «Выбрать» слева + 3 компактные кнопки действий справа
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Чекбокс выбора
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onSelectChanged(!isSelected) }
                        .padding(end = 8.dp)
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = onSelectChanged,
                        colors = CheckboxDefaults.colors(
                            checkedColor = TokyoNightPrimary,
                            uncheckedColor = TokyoNightBorder,
                            checkmarkColor = TokyoNightBackground
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Выбрать",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TokyoNightTextPrimary,
                            fontSize = 14.sp
                        )
                    )
                }

                // Кнопки: Информация, Загрузка, Настройки
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SquareIconButton(
                        icon = Icons.Default.Info,
                        contentDescription = "Информация",
                        tint = TokyoNightCyan,
                        onClick = { onClickInfo(device) }
                    )

                    SquareIconButton(
                        icon = Icons.Default.FileDownload,
                        contentDescription = "Загрузка",
                        tint = TokyoNightSecondary,
                        onClick = { onClickDownload(device) }
                    )

                    SquareIconButton(
                        icon = Icons.Default.Settings,
                        contentDescription = "Настройки",
                        tint = TokyoNightPrimary,
                        onClick = { onClickSettings(device) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Нижняя строка: Память LittleFS + Прогресс-бар
            val used = device.fsUsedKb
            val total = device.fsTotalKb
            val progress = (used.toFloat() / total.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Устройство: $used КБ / $total КБ",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TokyoNightTextSecondary,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = TokyoNightPrimary,
                trackColor = TokyoNightBackground
            )
        }
    }
}

@Composable
private fun SquareIconButton(
    icon: ImageVector,
    contentDescription: String,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = TokyoNightSurfaceVariant,
        border = BorderStroke(1.dp, TokyoNightBorder),
        modifier = modifier.size(36.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun DeviceModeBadge(
    mode: DeviceMode,
    isSetup: Boolean,
    modifier: Modifier = Modifier
) {
    val (badgeText, badgeColor) = when {
        isSetup || mode == DeviceMode.SETUP -> "SETUP" to TokyoNightOrange
        mode == DeviceMode.MASTER -> "MASTER" to TokyoNightCyan
        mode == DeviceMode.CLIENT -> "CLIENT" to TokyoNightSecondary
        mode == DeviceMode.HOST -> "HOST" to TokyoNightGreen
        else -> "DEVICE" to TokyoNightPrimary
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = badgeColor.copy(alpha = 0.15f),
        border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.5f))
    ) {
        Text(
            text = badgeText,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = badgeColor,
                fontSize = 11.sp
            )
        )
    }
}
