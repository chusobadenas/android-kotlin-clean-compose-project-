package com.jesusbadenas.kotlin_clean_compose_project.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class Network(
    private val context: Context
) {

    fun isConnected(): Boolean {
        var result = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.let { cm ->
            for (network in cm.allNetworks) {
                cm.getNetworkCapabilities(network)?.let {
                    if (it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    ) {
                        result = true
                    }
                }
            }
        }
        return result
    }
}
