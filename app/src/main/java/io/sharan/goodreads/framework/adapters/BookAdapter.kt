package io.sharan.goodreads.framework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book
import javax.inject.Inject

class BookAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<BookAdapter.BookItemViewHolder>() {

    class BookItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBookImage = itemView.findViewById<ImageView>(R.id.ivBookImage)
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvBookItemAmount = itemView.findViewById<TextView>(R.id.tvBookItemAmount)
        val tvBookItemPrice = itemView.findViewById<TextView>(R.id.tvBookItemPrice)


        fun bindBook(glide: RequestManager, book: Book) {
            glide.load(book.curImageUrl).into(ivBookImage)

            tvName.text = book.title
            val amountText = "${book.amount}x"
            tvBookItemAmount.text = amountText
            val priceText = "${book.price}€"
            tvBookItemPrice.text = priceText
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var books: List<Book>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_book_yt,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val book = books[position]
        holder.bindBook(glide, book)
    }
}