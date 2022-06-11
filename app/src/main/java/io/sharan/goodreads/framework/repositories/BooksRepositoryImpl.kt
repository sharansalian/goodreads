package io.sharan.goodreads.framework.repositories

import androidx.lifecycle.LiveData
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.local.BooksDao
import io.sharan.goodreads.framework.data.remote.PixabayAPI
import io.sharan.goodreads.framework.data.remote.responses.ImageResponse
import io.sharan.goodreads.framework.other.Resource
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksDao: BooksDao,
    private val pixabayAPI: PixabayAPI
) : BooksRepository {

    override suspend fun insertBook(book: Book) {
        booksDao.insert(book)
    }

    override suspend fun deleteBook(book: Book) {
        booksDao.delete(book)
    }

    override fun observeAllBooks(): LiveData<List<Book>> {
        return booksDao.getBooks()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return booksDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}