package io.sharan.goodreads.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book


class BooksAdapter : RecyclerView.Adapter<BookItemViewHolder>() {
    var books = listOf<Book>(Book(id = 1, "The Wizard of Once", "Cressida Cowell"))
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_book, parent, false) as TextView
        return BookItemViewHolder(textView = view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = books[position]
        holder.textView.text = item.title
    }

    override fun getItemCount(): Int = books.size
}