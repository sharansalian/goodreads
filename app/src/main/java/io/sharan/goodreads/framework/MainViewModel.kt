package io.sharan.goodreads.framework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sharan.goodreads.business.data.Book
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val booksDao: BooksDao): ViewModel() {

    init {
        viewModelScope.launch {
            val books = listOf<Book>(Book(id = 1, "The Wizard of Once", "Cressida Cowell"))
            books.forEach {
                booksDao.insert(it)
            }
        }
    }

    val books = booksDao.getBooks()

}