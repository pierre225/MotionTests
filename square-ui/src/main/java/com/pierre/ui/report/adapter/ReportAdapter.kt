package com.pierre.ui.report.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.pierre.ui.base.BasePagingAdapter
import com.pierre.ui.report.models.ReportMotion
import com.pierre.ui.report.viewholder.ReportViewHolder

/**
 * Reports paging adapter
 * 2 reports are considered the same if their startTime is the same
 */
class ReportAdapter(onItemClick: ((ReportMotion) -> Unit)) :
    BasePagingAdapter<ReportMotion>(onItemClick, diffCallback) {

    override fun create(parent: ViewGroup, viewType: Int) =
        ReportViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ReportMotion>() {

            override fun areItemsTheSame(oldItem: ReportMotion, newItem: ReportMotion) =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: ReportMotion, newItem: ReportMotion) =
                oldItem == newItem
        }
    }
}
