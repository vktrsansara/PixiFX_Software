package com.vktrsansara.app.pixifx

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.presentation.components.PixiFxBottomNavBar
import com.vktrsansara.app.pixifx.presentation.navigation.AppTab
import com.vktrsansara.app.pixifx.presentation.screens.constructor.ConstructorScreen
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerScreen
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerViewModel
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListScreen
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListViewModel
import com.vktrsansara.app.pixifx.presentation.screens.remote.RemoteScreen
import com.vktrsansara.app.pixifx.presentation.screens.settings.SettingsScreen
import com.vktrsansara.app.pixifx.presentation.theme.PixiFxTheme
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBackground
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KoinContext {
        PixiFxTheme {
            var currentTab by remember { mutableStateOf(AppTab.DEVICES) }
            var selectedDevice by remember { mutableStateOf<Device?>(null) }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(TokyoNightBackground)
                    .systemBarsPadding() // Pushes all UI strictly between top status bar and bottom navigation bar on Android
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Crossfade(targetState = currentTab) { tab ->
                        when (tab) {
                            AppTab.DEVICES -> {
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
                            AppTab.REMOTE -> {
                                RemoteScreen()
                            }
                            AppTab.CONSTRUCTOR -> {
                                ConstructorScreen()
                            }
                            AppTab.SETTINGS -> {
                                SettingsScreen()
                            }
                        }
                    }
                }

                PixiFxBottomNavBar(
                    selectedTab = currentTab,
                    onTabSelected = { newTab ->
                        currentTab = newTab
                    }
                )
            }
        }
    }
}