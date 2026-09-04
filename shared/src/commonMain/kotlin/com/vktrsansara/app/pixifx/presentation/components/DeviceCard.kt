package com.vktrsansara.app.pixifx.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.domain.model.DeviceMode
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBackground
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCard
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCyan
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightGreen
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightOrange
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSecondary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextPrimary

@Composable
fun DeviceCard(
    device: Device,
    isConnected: Boolean = false,
    onConnectClick: (Device) -> Unit,
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
            width = if (isConnected) 1.5.dp else 1.dp,
            color = if (isConnected) TokyoNightPrimary else TokyoNightBorder
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status glowing circle indicator
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(if (isConnected) TokyoNightGreen else TokyoNightCyan)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TokyoNightTextPrimary
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Mode Badge
                DeviceModeBadge(mode = device.mode, isSetup = device.isSetup)
            }

            Button(
                onClick = { onConnectClick(device) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isConnected) TokyoNightGreen else TokyoNightPrimary,
                    contentColor = TokyoNightBackground
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            ) {
                Text(
                    text = if (isConnected) "Подключено" else "Управление",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
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
