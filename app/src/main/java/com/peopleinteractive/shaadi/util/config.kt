package com.peopleinteractive.shaadi.util

import android.content.Context
import android.net.ConnectivityManager

fun isNetworkConnected(context: Context): Boolean {
    val nInfo = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        .activeNetworkInfo
    return nInfo != null && nInfo.isAvailable && nInfo.isConnected
}