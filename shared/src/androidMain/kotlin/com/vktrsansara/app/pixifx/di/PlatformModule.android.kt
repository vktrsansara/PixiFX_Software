package com.vktrsansara.app.pixifx.di

import com.vktrsansara.app.pixifx.data.discovery.AndroidNetworkInterfaceProvider
import com.vktrsansara.app.pixifx.data.discovery.AndroidUdpDeviceDiscovery
import com.vktrsansara.app.pixifx.data.discovery.INetworkInterfaceProvider
import com.vktrsansara.app.pixifx.data.discovery.IUdpDeviceDiscovery
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<INetworkInterfaceProvider> { AndroidNetworkInterfaceProvider() }
    single<IUdpDeviceDiscovery> { AndroidUdpDeviceDiscovery(dispatchers = get()) }
}
