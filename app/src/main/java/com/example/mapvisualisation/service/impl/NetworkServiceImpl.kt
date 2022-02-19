package com.example.mapvisualisation.service.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.mapvisualisation.service.NetworkService

class NetworkServiceImpl(
    private val context: Context,
) : NetworkService {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.getNetworkCapabilities(
                connectivityManager.activeNetwork
            )

            activeNetwork != null && (
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo

            networkInfo != null && networkInfo.isConnected
        }
    }
}
