package com.example.mapvisualisation.service

/**
 * A service that check the network connection
 */
interface NetworkService {

    /**
     * Returns true if device has active network state
     */
    fun isConnected(): Boolean
}
