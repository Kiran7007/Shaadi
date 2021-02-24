package com.peopleinteractive.shaadi.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun getStandardTime(timeStamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(timeStamp)
    }
}