package io.sharan.goodreads.framework

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book

class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.txt_title)
    val author: TextView = itemView.findViewById(R.id.txt_author)
    val cover: ImageView = itemView.findViewById(R.id.iv_cover)
}

fun BookItemViewHolder.bind(book: Book) {
    title.text = book.title
    author.text = book.author
}