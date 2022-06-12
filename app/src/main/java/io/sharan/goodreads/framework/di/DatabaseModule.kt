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
import io.sharan.goodreads.framework.data.remote.PixabayAPI
import io.sharan.goodreads.framework.other.Constants
import io.sharan.goodreads.framework.repositories.BooksRepository
import io.sharan.goodreads.framework.repositories.BooksRepositoryImpl
import javax.inject.Singleton

// Modules are use to add bindings to Hilt
// Here we can provide bindings for types that cannot be constructor injected
// such as interfaces and classes which are not in your project
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // We cannot constructor inject BooksDatabase as Room generates the instance
    @Provides
    @Singleton
    fun provideBookDatabase(
        @ApplicationContext context: Context // @ApplicationContext is a default binding
    ): BooksDatabase {
        return Room.databaseBuilder(
            context,
            BooksDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    //Here, database is a transitive dependency(we also need to tell Hilt how to provide an instance of BookDatabase
    @Provides
    @Singleton
    fun provideBooksDao(database: BooksDatabase): BooksDao {
       return database.booksDao
    }


    @Provides
    @Singleton
    fun provideBookRepositoryImpl(dao: BooksDao, pixabayAPI: PixabayAPI) : BooksRepository {
        return BooksRepositoryImpl(dao, pixabayAPI)
    }
}