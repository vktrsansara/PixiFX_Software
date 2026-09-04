package com.vktrsansara.app.pixifx.data.discovery

import com.vktrsansara.app.pixifx.core.utils.CoroutineDispatchers
import com.vktrsansara.app.pixifx.data.network.dto.DeviceDto
import com.vktrsansara.app.pixifx.domain.model.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketTimeoutException

class JvmUdpDeviceDiscovery(
    private val dispatchers: CoroutineDispatchers,
    private val json: Json = Json { ignoreUnknownKeys = true; isLenient = true }
) : IUdpDeviceDiscovery {

    override fun discoverViaBroadcast(
        port: Int,
        payload: String,
        listenDurationMs: Long
    ): Flow<Device> = flow {
        val seenIds = mutableSetOf<String>()
        var socket: DatagramSocket? = null

        try {
            socket = DatagramSocket().apply {
                broadcast = true
                soTimeout = 250 // Interval for checking cancellation and loop time
            }

            val requestData = payload.toByteArray(Charsets.UTF_8)
            val globalBroadcast = InetAddress.getByName("255.255.255.255")

            // Send global broadcast
            socket.send(DatagramPacket(requestData, requestData.size, globalBroadcast, port))

            // Also send broadcast to each active network interface broadcast address
            try {
                val interfaces = NetworkInterface.getNetworkInterfaces()
                while (interfaces.hasMoreElements()) {
                    val networkInterface = interfaces.nextElement()
                    if (!networkInterface.isUp || networkInterface.isLoopback) continue

                    for (interfaceAddress in networkInterface.interfaceAddresses) {
                        val broadcast = interfaceAddress.broadcast
                        if (broadcast != null) {
                            socket.send(DatagramPacket(requestData, requestData.size, broadcast, port))
                        }
                    }
                }
            } catch (_: Exception) {
                // Ignore interface lookup failure, global broadcast already sent
            }

            val buffer = ByteArray(2048)
            val receivePacket = DatagramPacket(buffer, buffer.size)
            val startTime = System.currentTimeMillis()

            while (System.currentTimeMillis() - startTime < listenDurationMs) {
                try {
                    socket.receive(receivePacket)
                    val senderIp = receivePacket.address.hostAddress ?: continue
                    val responseJson = String(receivePacket.data, 0, receivePacket.length, Charsets.UTF_8)

                    val dto = json.decodeFromString<DeviceDto>(responseJson)
                    val device = dto.toDomain(fallbackIp = senderIp)

                    if (seenIds.add(device.id)) {
                        emit(device)
                    }
                } catch (_: SocketTimeoutException) {
                    // Interval timeout, continue listening
                } catch (_: Exception) {
                    // Ignore non-json or foreign UDP packets
                }
            }
        } catch (_: Exception) {
            // Ignore socket creation / general error
        } finally {
            socket?.close()
        }
    }.flowOn(dispatchers.io)
}
