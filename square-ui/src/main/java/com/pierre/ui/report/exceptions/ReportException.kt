package com.pierre.ui.report.exceptions

/**
 * Example exception that we can throw
 */
data class ReportException(
    override val message: String
): Exception()
