package com.pierre.ui.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Currently unused but this is an example of a base adapter that wouldn't use paging
 */
abstract class BaseAdapter<T : Any>(
    private val onItemClick : ((T) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    // Accessible from children
    protected var items = emptyList<T>()

    abstract fun create(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        create(parent, viewType).apply {
            if (onItemClick != null) setOnClickListener(onItemClick)
        }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    /**
     * Depending on how we update the data (append / replace...)
     * We should implement how we update the list and the viewholders.
     * Generally we should avoid notifyDataSetChanged and use a more granular approach
     */
    fun updateItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

}