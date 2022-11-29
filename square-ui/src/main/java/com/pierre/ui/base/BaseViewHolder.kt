package com.pierre.ui.base

import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.R
import androidx.recyclerview.widget.RecyclerView

/**
 * A base view holder which usually works with the BaseAdapter or the BasePagingAdapter.
 * It has a onclickListener ready and will inflate the layout passed
 */
abstract class BaseViewHolder<T : Any>(itemView: View): RecyclerView.ViewHolder(itemView) {

    constructor(parent: ViewGroup, @LayoutRes layout: Int) : this(inflate(parent, layout))

    private lateinit var item: T

    open fun bind(item : T) {
        this.item = item
    }

    fun setOnClickListener(onClick : (T) -> Unit) {
        itemView.setOnClickListener { onClick.invoke(item) }
    }

    companion object {
        private fun inflate(parent: ViewGroup, @LayoutRes layout: Int) =
            LayoutInflater
                .from(ContextThemeWrapper(parent.context, R.style.Theme_AppCompat))
                .inflate(layout, parent, false)
    }

}