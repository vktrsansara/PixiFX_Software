package com.vktrsansara.app.pixifx.data.discovery

interface INetworkInterfaceProvider {
    /**
     * Returns list of active IPv4 subnet prefixes (e.g. ["192.168.1.", "192.168.0."]).
     */
    fun getActiveSubnetPrefixes(): List<String>
}
