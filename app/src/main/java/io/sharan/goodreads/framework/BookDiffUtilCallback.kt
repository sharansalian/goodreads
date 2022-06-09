package io.sharan.goodreads.framework

import androidx.recyclerview.widget.DiffUtil

class DataItemDiffUtilCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
       return oldItem == newItem
    }
}