package com.vktrsansara.app.pixifx.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextSecondary

@Composable
fun BrightnessSlider(
    brightness: Int,
    onBrightnessChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Яркость",
                style = MaterialTheme.typography.titleSmall,
                color = TokyoNightTextPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${(brightness * 100) / 255}%",
                style = MaterialTheme.typography.bodySmall,
                color = TokyoNightTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Slider(
            value = brightness.toFloat(),
            onValueChange = { onBrightnessChange(it.toInt()) },
            valueRange = 0f..255f,
            colors = SliderDefaults.colors(
                thumbColor = TokyoNightPrimary,
                activeTrackColor = TokyoNightPrimary,
                inactiveTrackColor = TokyoNightBorder
            )
        )
    }
}
