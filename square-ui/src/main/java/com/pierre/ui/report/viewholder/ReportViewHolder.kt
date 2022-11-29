package com.pierre.ui.report.viewholder

import android.view.ViewGroup
import com.pierre.ui.R
import com.pierre.ui.base.BaseViewHolder
import com.pierre.ui.databinding.ReportViewHolderBinding
import com.pierre.ui.report.models.ReportMotion

class ReportViewHolder(parent: ViewGroup) :
    BaseViewHolder<ReportMotion>(parent, R.layout.report_view_holder) {

    private val binding = ReportViewHolderBinding.bind(itemView)

    private val context get() = binding.root.context

    override fun bind(item: ReportMotion) {
        super.bind(item)

        binding.apply {
            date.text = context.getString(R.string.date, item.date)
            duration.text = context.getString(R.string.duration, item.duration)
            recordedPositions.text = context.getString(R.string.recorded_positions, item.positionCount)
            reportViewHolder.setBackgroundColor(context.getColor(
                if (item.exceeded) R.color.invalid_color
                else R.color.valid_color))
        }
    }
}