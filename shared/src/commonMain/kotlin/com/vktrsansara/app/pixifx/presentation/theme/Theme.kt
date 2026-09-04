package com.vktrsansara.app.pixifx.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val TokyoNightColorScheme = darkColorScheme(
    primary = TokyoNightPrimary,
    onPrimary = TokyoNightBackground,
    primaryContainer = TokyoNightSurfaceVariant,
    onPrimaryContainer = TokyoNightCyan,
    
    secondary = TokyoNightSecondary,
    onSecondary = TokyoNightBackground,
    secondaryContainer = TokyoNightSurface,
    onSecondaryContainer = TokyoNightSecondary,
    
    tertiary = TokyoNightCyan,
    onTertiary = TokyoNightBackground,
    
    background = TokyoNightBackground,
    onBackground = TokyoNightTextPrimary,
    
    surface = TokyoNightSurface,
    onSurface = TokyoNightTextPrimary,
    surfaceVariant = TokyoNightSurfaceVariant,
    onSurfaceVariant = TokyoNightTextSecondary,
    
    error = TokyoNightRed,
    onError = TokyoNightBackground,
    
    outline = TokyoNightBorder,
    outlineVariant = TokyoNightBorder
)

@Composable
fun PixiFxTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TokyoNightColorScheme,
        typography = Typography,
        content = content
    )
}
