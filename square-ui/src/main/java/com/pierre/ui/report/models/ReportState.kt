package com.pierre.ui.report.models

import androidx.annotation.StringRes
import androidx.paging.PagingData

sealed class ReportState {

    data class ReportResultsState(val pagedReports: PagingData<ReportMotion>) : ReportState()
    object ReportLoadingState : ReportState()
    data class ReportErrorState(@StringRes val message: Int, val retry: Boolean) : ReportState()

}


