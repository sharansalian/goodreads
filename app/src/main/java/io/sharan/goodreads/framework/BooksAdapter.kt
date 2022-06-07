package io.sharan.goodreads.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book


class BooksAdapter : RecyclerView.Adapter<BookItemViewHolder>() {
    var books = listOf<Book>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return Companion.from(parent)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    companion object {
        private fun from(parent: ViewGroup): BookItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_book, parent, false)
            return BookItemViewHolder(view)
        }
    }
}