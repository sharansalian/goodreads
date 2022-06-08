package io.sharan.goodreads.framework.books

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.BooksDao
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val booksDao: BooksDao) : ViewModel() {

    private val _navigateToSleepDetail = MutableLiveData<Int?>()
    val navigateToSleepDetail
        get() = _navigateToSleepDetail

    init {
        viewModelScope.launch {
            val books = listOf<Book>(
                Book(id = 1, "The Wizard of Once", "Cressida Cowell"),
                Book(2, "The Metamorphosis", "Franz Kafka")
            )
            books.forEach {
                booksDao.insert(it)
            }
        }
    }

    val books = booksDao.getBooks()

    fun onBookClicked(id: Int) {
        _navigateToSleepDetail.value = id
    }

    fun onBookDetailNavigated() {
        _navigateToSleepDetail.value = null
    }

}