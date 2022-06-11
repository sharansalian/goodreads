package io.sharan.goodreads.framework.repositories

import androidx.lifecycle.LiveData
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.remote.responses.ImageResponse
import io.sharan.goodreads.framework.other.Resource

interface BooksRepository {

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    fun observeAllBooks(): LiveData<List<Book>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}