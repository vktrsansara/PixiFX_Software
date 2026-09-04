package com.vktrsansara.app.pixifx.data.discovery

import java.net.Inet4Address
import java.net.NetworkInterface

class AndroidNetworkInterfaceProvider : INetworkInterfaceProvider {
    override fun getActiveSubnetPrefixes(): List<String> {
        val prefixes = mutableSetOf<String>()
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces() ?: return emptyList()
            for (networkInterface in interfaces) {
                if (!networkInterface.isUp || networkInterface.isLoopback) continue
                for (address in networkInterface.inetAddresses) {
                    if (address is Inet4Address && !address.isLoopbackAddress) {
                        val hostAddress = address.hostAddress ?: continue
                        val parts = hostAddress.split(".")
                        if (parts.size == 4) {
                            val prefix = "${parts[0]}.${parts[1]}.${parts[2]}."
                            prefixes.add(prefix)
                        }
                    }
                }
            }
        } catch (_: Exception) {
            // Ignore network inspection errors
        }
        return prefixes.toList()
    }
}
