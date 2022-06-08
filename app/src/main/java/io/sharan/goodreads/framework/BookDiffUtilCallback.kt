package io.sharan.goodreads.framework

import androidx.recyclerview.widget.DiffUtil
import io.sharan.goodreads.business.data.Book

class BookDiffUtilCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
       return oldItem == newItem
    }
}