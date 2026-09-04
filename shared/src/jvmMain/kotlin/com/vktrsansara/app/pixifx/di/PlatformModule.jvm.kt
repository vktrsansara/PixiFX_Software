package com.vktrsansara.app.pixifx.di

import com.vktrsansara.app.pixifx.data.discovery.INetworkInterfaceProvider
import com.vktrsansara.app.pixifx.data.discovery.IUdpDeviceDiscovery
import com.vktrsansara.app.pixifx.data.discovery.JvmNetworkInterfaceProvider
import com.vktrsansara.app.pixifx.data.discovery.JvmUdpDeviceDiscovery
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<INetworkInterfaceProvider> { JvmNetworkInterfaceProvider() }
    single<IUdpDeviceDiscovery> { JvmUdpDeviceDiscovery(dispatchers = get()) }
}
