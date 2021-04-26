package com.ecom.sample.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil{

    /**
     * Checks the current network status.
     */
    @JvmStatic
    fun isNetworkConnected(context: Context): Boolean {
        val nInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo
        return nInfo != null && nInfo.isAvailable && nInfo.isConnected
    }
}