package io.sharan.goodreads.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.databinding.ItemBookBinding

//ListAdapter keeps track of the list
class BooksAdapter(private val clickListener: BookListener) :
    ListAdapter<Book, BookItemViewHolder>(BookDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val book = getItem(position)
        holder.binding.book = book
        holder.binding.clickListener = clickListener
    }

    companion object {
        private fun from(parent: ViewGroup): BookItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemBookBinding.inflate(layoutInflater, parent, false)
            return BookItemViewHolder(binding)
        }
    }
}

class BookListener(val clickListener: (bookId: Int) -> Unit) {
    fun onClick(book: Book) = clickListener(book.id)
}