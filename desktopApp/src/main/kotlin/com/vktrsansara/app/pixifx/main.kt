package com.vktrsansara.app.pixifx

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.vktrsansara.app.pixifx.di.initKoin

fun main() {
    initKoin()
    application {
        val windowState = rememberWindowState(width = 860.dp, height = 720.dp)
        Window(
            onCloseRequest = ::exitApplication,
            title = "PixiFX Controller",
            state = windowState
        ) {
            App()
        }
    }
}