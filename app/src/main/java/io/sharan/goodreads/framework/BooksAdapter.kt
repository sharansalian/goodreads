package io.sharan.goodreads.framework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.databinding.ItemBookBinding
import kotlinx.coroutines.*

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

//ListAdapter keeps track of the list
class BooksAdapter(private val clickListener: BookListener) :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffUtilCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    /**
     * It doesn't matter much for a short list with one header,
     * but you should not do list manipulation in addHeaderAndSubmitList()
     * on the UI thread. Imagine a list with hundreds of items, multiple headers,
     * and logic to decide where items need to be inserted. This work belongs in a coroutine.
     */
    fun addHeaderAndSubmitList(list: List<Book>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.BookItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = getItem(position)

        when (holder) {
            is BookItemViewHolder -> {
                holder.binding.book = (dataItem as DataItem.BookItem).book
                holder.binding.clickListener = clickListener
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.BookItem -> ITEM_VIEW_TYPE_ITEM
        }
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

/***
 * A sealed class defines a closed type, which means that all subclasses of DataItem must be defined in this file.
 * As a result, the number of subclasses is known to the compiler.
 * It's not possible for another part of your code to define a new type of DataItem that could break your adapter.
 *
 */
sealed class DataItem {
    abstract val id: Int

    data class BookItem(val book: Book) : DataItem() {
        override val id: Int
            get() = book.id
    }

    object Header : DataItem() {
        override val id: Int
            get() = Int.MIN_VALUE
    }
}