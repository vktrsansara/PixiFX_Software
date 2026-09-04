package com.vktrsansara.app.pixifx.di

import com.vktrsansara.app.pixifx.core.utils.CoroutineDispatchers
import com.vktrsansara.app.pixifx.core.utils.DefaultCoroutineDispatchers
import com.vktrsansara.app.pixifx.data.discovery.DeviceDiscoveryService
import com.vktrsansara.app.pixifx.data.discovery.INetworkDiscovery
import com.vktrsansara.app.pixifx.data.network.DeviceTransportClient
import com.vktrsansara.app.pixifx.data.network.KtorClientFactory
import com.vktrsansara.app.pixifx.data.repository.DeviceRepositoryImpl
import com.vktrsansara.app.pixifx.data.repository.EffectRepositoryImpl
import com.vktrsansara.app.pixifx.domain.repository.IDeviceRepository
import com.vktrsansara.app.pixifx.domain.repository.IEffectRepository
import com.vktrsansara.app.pixifx.domain.usecase.ConnectToDeviceUseCase
import com.vktrsansara.app.pixifx.domain.usecase.DiscoverDevicesUseCase
import com.vktrsansara.app.pixifx.domain.usecase.SetBrightnessUseCase
import com.vktrsansara.app.pixifx.domain.usecase.SetEffectUseCase
import com.vktrsansara.app.pixifx.presentation.screens.controller.ControllerViewModel
import com.vktrsansara.app.pixifx.presentation.screens.devicelist.DeviceListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val commonModule: Module = module {
    // Dispatchers
    single<CoroutineDispatchers> { DefaultCoroutineDispatchers() }

    // Network & HTTP
    single { KtorClientFactory.create() }
    single { DeviceTransportClient(httpClient = get()) }

    // Discovery & Repositories
    single<INetworkDiscovery> {
        DeviceDiscoveryService(
            transportClient = get(),
            udpDiscovery = get(),
            dispatchers = get()
        )
    }

    single<IDeviceRepository> {
        DeviceRepositoryImpl(
            discoveryService = get(),
            transportClient = get()
        )
    }

    single<IEffectRepository> {
        EffectRepositoryImpl(
            transportClient = get()
        )
    }

    // UseCases
    factory { DiscoverDevicesUseCase(deviceRepository = get()) }
    factory { ConnectToDeviceUseCase(deviceRepository = get()) }
    factory { SetEffectUseCase(effectRepository = get()) }
    factory { SetBrightnessUseCase(effectRepository = get()) }

    // ViewModels
    viewModel {
        DeviceListViewModel(
            discoverDevicesUseCase = get(),
            connectToDeviceUseCase = get(),
            deviceRepository = get()
        )
    }

    viewModel {
        ControllerViewModel(
            setEffectUseCase = get(),
            setBrightnessUseCase = get()
        )
    }
}
