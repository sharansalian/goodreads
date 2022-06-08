package io.sharan.goodreads.framework

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.sharan.goodreads.business.data.Book


@BindingAdapter("withSpecialCharacters")
fun TextView.authorNameWithSpecialCharacter(book: Book) {
    text = "@@${book.author}@@"
}