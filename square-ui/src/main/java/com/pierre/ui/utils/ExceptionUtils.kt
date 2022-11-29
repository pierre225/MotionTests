package com.pierre.ui.utils

import com.pierre.ui.R
import com.pierre.ui.report.exceptions.ReportException

/**
 * Utils class to help us handle exceptions
 * This one is very simple because we only consider the ReportException but ideally we should consider a lot more
 */
object ExceptionUtils {

    /**
     * Returns a string resource from a type of exception so we can show the user what went wrong
     */
    fun messageFromException(e : Exception) = when (e) {
        is ReportException -> R.string.reporting_error
        else -> R.string.generic_error
    }

    /**
     * Returns true if the user can retry the previous action false otherwise
     */
    fun canRetry(e : Exception) = when (e) {
        is ReportException -> true
        else -> false
    }
}