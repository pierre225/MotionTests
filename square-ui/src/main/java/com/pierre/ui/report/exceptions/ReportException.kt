package com.pierre.ui.report.exceptions

import androidx.annotation.StringRes

/**
 * Example exception that we can throw
 */
data class ReportException(
    override val message: String
): Exception()
