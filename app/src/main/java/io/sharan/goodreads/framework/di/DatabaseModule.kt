package io.sharan.goodreads.framework.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sharan.goodreads.framework.data.local.BooksDao
import io.sharan.goodreads.framework.data.local.BooksDatabase
import javax.inject.Singleton

// Modules are use to add bindings to Hilt
// Here we can provide bindings for types that cannot be constructor injected
// such as interfaces and classes which are not in your project
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    // We cannot constructor inject BooksDatabase as Room generates the instance
    @Provides
    @Singleton
    fun provideBookDatabase(
        @ApplicationContext context: Context // @ApplicationContext is a default binding
    ): BooksDatabase {
        return Room.databaseBuilder(
            context,
            BooksDatabase::class.java,
            "book_database"
        ).fallbackToDestructiveMigration().build()
    }

    //Here, database is a transitive dependency(we also need to tell Hilt how to provide an instance of BookDatabase
    @Provides
    fun provideBooksDao(database: BooksDatabase): BooksDao {
       return database.booksDao
    }
}