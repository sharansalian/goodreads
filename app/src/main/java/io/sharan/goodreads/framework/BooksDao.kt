package io.sharan.goodreads.framework

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.sharan.goodreads.business.data.Book

@Dao
interface BooksDao {

    @Insert
    suspend fun insert(book: Book)

    @Query("SELECT * from books")
    fun getBooks(): LiveData<List<Book>>

}