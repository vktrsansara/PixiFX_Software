package com.vktrsansara.app.pixifx.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vktrsansara.app.pixifx.domain.model.RgbColor
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCyan
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightGreen
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightOrange
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightRed
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSecondary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightYellow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPalettePicker(
    selectedColor: RgbColor,
    onColorSelected: (RgbColor) -> Unit,
    modifier: Modifier = Modifier
) {
    val presetColors = listOf(
        RgbColor(255, 0, 0) to "Красный",
        RgbColor(0, 255, 0) to "Зеленый",
        RgbColor(0, 0, 255) to "Синий",
        RgbColor(255, 255, 0) to "Желтый",
        RgbColor(0, 255, 255) to "Бирюзовый",
        RgbColor(255, 0, 255) to "Пурпурный",
        RgbColor(255, 128, 0) to "Оранжевый",
        RgbColor(255, 255, 255) to "Белый"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Палитра цветов",
            style = MaterialTheme.typography.titleSmall,
            color = TokyoNightTextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            presetColors.forEach { (rgb, _) ->
                val composeColor = Color(rgb.r, rgb.g, rgb.b)
                val isSelected = selectedColor == rgb

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(composeColor)
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) TokyoNightTextPrimary else TokyoNightBorder,
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(rgb) }
                )
            }
        }
    }
}
