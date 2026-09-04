package com.vktrsansara.app.pixifx.data.discovery

import com.vktrsansara.app.pixifx.core.utils.CoroutineDispatchers
import com.vktrsansara.app.pixifx.data.network.DeviceTransportClient
import com.vktrsansara.app.pixifx.domain.model.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DeviceDiscoveryService(
    private val transportClient: DeviceTransportClient,
    private val udpDiscovery: IUdpDeviceDiscovery,
    private val dispatchers: CoroutineDispatchers
) : INetworkDiscovery {

    override fun discoverDevices(): Flow<List<Device>> = channelFlow {
        val foundDevices = mutableListOf<Device>()
        val seenIds = mutableSetOf<String>()
        val mutex = Mutex()

        suspend fun addAndSend(device: Device) {
            val listToSend = mutex.withLock {
                if (seenIds.add(device.id)) {
                    foundDevices.add(device)
                    foundDevices.toList()
                } else null
            }
            if (listToSend != null) {
                send(listToSend)
            }
        }

        // 1. Fallback: Quick direct HTTP check for Access Point default IPs (10.10.1.1, 192.168.4.1)
        val apJob = launch(dispatchers.io) {
            val apTargets = listOf("10.10.1.1", "192.168.4.1")
            for (ip in apTargets) {
                val result = transportClient.getDeviceInfo(ip, timeoutMillis = 600)
                result.getOrNull()?.let { dto ->
                    val device = dto.toDomain(fallbackIp = ip)
                    addAndSend(device)
                }
            }
        }

        // 2. Primary: Instant UDP Broadcast Discovery (port 8080, payload "PIXIFX_DISCOVER")
        val udpJob = launch(dispatchers.io) {
            udpDiscovery.discoverViaBroadcast(
                port = 8080,
                payload = "PIXIFX_DISCOVER",
                listenDurationMs = 2000
            ).collect { device ->
                addAndSend(device)
            }
        }

        apJob.join()
        udpJob.join()

        // Final emission of all collected devices
        val finalList = mutex.withLock { foundDevices.toList() }
        send(finalList)
    }.flowOn(dispatchers.io)
}
