package com.vktrsansara.app.pixifx

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerScreen
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerViewModel
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListScreen
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListViewModel
import com.vktrsansara.app.pixifx.presentation.theme.PixiFxTheme
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBackground
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinContext {
        PixiFxTheme {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TokyoNightBackground)
                    .systemBarsPadding() // Pushes all UI strictly between status bar and navigation bar
            ) {
                var selectedDevice by remember { mutableStateOf<Device?>(null) }

                Crossfade(targetState = selectedDevice) { device ->
                    if (device == null) {
                        val deviceListViewModel = koinViewModel<DeviceListViewModel>()
                        DeviceListScreen(
                            viewModel = deviceListViewModel,
                            onNavigateToController = { connectedDevice ->
                                selectedDevice = connectedDevice
                            }
                        )
                    } else {
                        val controllerViewModel = koinViewModel<ControllerViewModel>()
                        ControllerScreen(
                            device = device,
                            viewModel = controllerViewModel,
                            onNavigateBack = {
                                selectedDevice = null
                            }
                        )
                    }
                }
            }
        }
    }
}