package io.sharan.goodreads.repositories

import androidx.lifecycle.LiveData
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.remote.responses.ImageResponse
import io.sharan.goodreads.framework.other.Resource
import io.sharan.goodreads.framework.repositories.BooksRepository

class FakeBooksRepositoryImpl : BooksRepository {
    override suspend fun insertBook(book: Book) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBook(book: Book) {
        TODO("Not yet implemented")
    }

    override fun observeAllBooks(): LiveData<List<Book>> {
        TODO("Not yet implemented")
    }

    override fun observeTotalPrice(): LiveData<Float> {
        TODO("Not yet implemented")
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        TODO("Not yet implemented")
    }
}