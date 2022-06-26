package io.sharan.goodreads.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.remote.responses.ImageResponse
import io.sharan.goodreads.framework.other.Resource
import io.sharan.goodreads.framework.repositories.BooksRepository

class FakeBooksRepositoryAndroidTest : BooksRepository {

    private val books = mutableListOf<Book>()

    private val observableBooks = MutableLiveData<List<Book>>(books)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableBooks.postValue(books)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return books.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertBook(book: Book) {
        books.add(book)
        refreshLiveData()
    }

    override suspend fun deleteBook(book: Book) {
        books.remove(book)
        refreshLiveData()
    }

    override fun observeAllBooks(): LiveData<List<Book>> {
        return observableBooks
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(emptyList(), 0, 0))
        }
    }
}