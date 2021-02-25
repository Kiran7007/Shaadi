package com.peopleinteractive.shaadi.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * DateUtil responsible for all the data & time related operation.
 */
object DateUtil {

    /**
     * Gets the date time in standard format.
     */
    fun getStandardTime(timeStamp: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        return sdf.format(timeStamp)
    }
}