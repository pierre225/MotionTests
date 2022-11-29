package com.pierre.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd  HH:mm:ss")
        return format.format(date)
    }

}