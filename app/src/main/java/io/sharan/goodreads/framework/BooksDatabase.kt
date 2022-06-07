package io.sharan.goodreads.framework

import androidx.room.Database
import androidx.room.RoomDatabase
import io.sharan.goodreads.business.data.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val booksDao: BooksDao

}