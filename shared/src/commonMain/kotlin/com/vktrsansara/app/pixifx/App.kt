package com.vktrsansara.app.pixifx

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerScreen
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerViewModel
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListScreen
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListViewModel
import com.vktrsansara.app.pixifx.presentation.theme.PixiFxTheme
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinContext {
        PixiFxTheme {
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