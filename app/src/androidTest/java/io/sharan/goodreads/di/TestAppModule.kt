package io.sharan.goodreads.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sharan.goodreads.framework.data.local.BooksDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun providesInMemoryDb(@ApplicationContext context: Context) = Room.inMemoryDatabaseBuilder(
        context,
        BooksDatabase::class.java
    ).allowMainThreadQueries()
        .build()
}