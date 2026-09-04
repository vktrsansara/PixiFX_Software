package com.vktrsansara.app.pixifx.presentation.screens.devicelist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vktrsansara.app.pixifx.domain.model.Device
import com.vktrsansara.app.pixifx.presentation.components.DeviceCard
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBackground
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightBorder
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCard
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightCyan
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSurface
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightSurfaceVariant
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextPrimary
import com.vktrsansara.app.pixifx.presentation.theme.TokyoNightTextSecondary
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    viewModel: DeviceListViewModel,
    onNavigateToController: (Device) -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is DeviceListEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is DeviceListEffect.NavigateToController -> {
                    onNavigateToController(effect.device)
                }
            }
        }
    }

    if (state.showDirectIpDialog) {
        DirectIpConnectDialog(
            currentIp = state.directIpInput,
            isLoading = state.isConnectingDirect,
            onIpChange = { viewModel.processIntent(DeviceListIntent.UpdateDirectIpInput(it)) },
            onConnect = { viewModel.processIntent(DeviceListIntent.ConnectDirectIp(state.directIpInput)) },
            onDismiss = { viewModel.processIntent(DeviceListIntent.SetDirectIpDialogVisible(false)) }
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(), // Guarantees layout is strictly between top status bar & bottom nav bar
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = TokyoNightBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // 1. HEADER (TopAppBar)
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0, 0, 0, 0),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = TokyoNightCyan,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "PixiFX",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = TokyoNightCyan
                            )
                        )
                    }
                },
                actions = {
                    // Search / Refresh Icon Button (Left action)
                    if (state.isSearching) {
                        val infiniteTransition = rememberInfiniteTransition()
                        val angle by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            )
                        )
                        IconButton(
                            onClick = { },
                            enabled = false
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Поиск...",
                                tint = TokyoNightCyan,
                                modifier = Modifier.rotate(angle)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { viewModel.processIntent(DeviceListIntent.StartDiscovery) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Поиск",
                                tint = TokyoNightTextPrimary
                            )
                        }
                    }

                    // Direct IP Icon Button (Right action)
                    IconButton(
                        onClick = { viewModel.processIntent(DeviceListIntent.SetDirectIpDialogVisible(true)) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Dns,
                            contentDescription = "Ввести IP",
                            tint = TokyoNightTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TokyoNightSurface,
                    titleContentColor = TokyoNightTextPrimary
                )
            )
        },
        // 3. FOOTER (BottomBar - Empty placeholder for future tabs)
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                color = TokyoNightSurface,
                border = BorderStroke(width = 1.dp, color = TokyoNightBorder)
            ) {
                // Empty for future navigation tabs
            }
        }
    ) { paddingValues ->
        // 2. WORK AREA (Middle block)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(visible = state.isSearching) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = TokyoNightCyan,
                    trackColor = TokyoNightSurfaceVariant
                )
            }

            if (state.devices.isEmpty()) {
                EmptyDeviceListContent(
                    isSearching = state.isSearching,
                    onStartDiscovery = { viewModel.processIntent(DeviceListIntent.StartDiscovery) },
                    onOpenDirectIp = { viewModel.processIntent(DeviceListIntent.SetDirectIpDialogVisible(true)) },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = state.devices,
                        key = { it.id }
                    ) { device ->
                        DeviceCard(
                            device = device,
                            isConnected = state.connectedDevice?.id == device.id,
                            onConnectClick = {
                                viewModel.processIntent(DeviceListIntent.ConnectToDevice(device))
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DirectIpConnectDialog(
    currentIp: String,
    isLoading: Boolean,
    onIpChange: (String) -> Unit,
    onConnect: () -> Unit,
    onDismiss: () -> Unit
) {
    val quickTargets = listOf(
        "10.10.1.1" to "AP default",
        "192.168.1.150" to "Static default",
        "pixifx.local" to "mDNS",
        "192.168.4.1" to "ESP AP"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = TokyoNightCard,
        modifier = Modifier.widthIn(max = 440.dp),
        title = {
            Text(
                text = "Прямое подключение по IP",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TokyoNightTextPrimary
                )
            )
        },
        text = {
            Column {
                Text(
                    text = "Введите IP-адрес или имя хоста ESP8266 контроллера:",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TokyoNightTextSecondary)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = currentIp,
                    onValueChange = onIpChange,
                    singleLine = true,
                    placeholder = { Text("10.10.1.1 или 192.168.1.X") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TokyoNightTextPrimary,
                        unfocusedTextColor = TokyoNightTextPrimary,
                        focusedBorderColor = TokyoNightCyan,
                        unfocusedBorderColor = TokyoNightBorder,
                        cursorColor = TokyoNightCyan
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Быстрый выбор:",
                    style = MaterialTheme.typography.labelSmall.copy(color = TokyoNightTextSecondary)
                )

                Spacer(modifier = Modifier.height(6.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickTargets.forEach { (target, label) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TokyoNightSurfaceVariant,
                            border = BorderStroke(1.dp, TokyoNightBorder),
                            modifier = Modifier.clickable { onIpChange(target) }
                        ) {
                            Text(
                                text = "$target ($label)",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = TokyoNightCyan,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConnect,
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = TokyoNightPrimary,
                    contentColor = TokyoNightBackground
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = TokyoNightBackground
                    )
                } else {
                    Text("Подключить")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = TokyoNightTextSecondary)
            ) {
                Text("Отмена")
            }
        }
    )
}

@Composable
private fun EmptyDeviceListContent(
    isSearching: Boolean,
    onStartDiscovery: () -> Unit,
    onOpenDirectIp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 440.dp) // Constrained width on desktop & tablet
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = TokyoNightCard),
            border = BorderStroke(1.dp, TokyoNightBorder),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Material Icon indicator
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    TokyoNightCyan.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                        .border(1.dp, TokyoNightCyan.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSearching) Icons.Default.Refresh else Icons.Default.Sensors,
                        contentDescription = null,
                        tint = TokyoNightCyan,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = if (isSearching) "Поиск контроллеров..." else "Устройства не найдены",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TokyoNightTextPrimary
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isSearching) {
                        "Опрос AP 10.10.1.1, mDNS pixifx.local и параллельный скан всех локальных подсетей..."
                    } else {
                        "Подключитесь к Wi-Fi сети контроллера (Pixi_Setup / Pixi_XXXXXX) или убедитесь, что ПК и контроллер в одной Wi-Fi сети."
                    },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TokyoNightTextSecondary
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (!isSearching) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onStartDiscovery,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TokyoNightPrimary,
                                contentColor = TokyoNightBackground
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Сканировать",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        OutlinedButton(
                            onClick = onOpenDirectIp,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = TokyoNightCyan
                            ),
                            border = BorderStroke(1.dp, TokyoNightBorder),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "Ввести IP",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
