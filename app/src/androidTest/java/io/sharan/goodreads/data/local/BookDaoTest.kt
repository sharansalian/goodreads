package io.sharan.goodreads.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.local.BooksDao
import io.sharan.goodreads.framework.data.local.BooksDatabase
import io.sharan.goodreads.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

/**
 * JUnit is used for tests in JVM
 * We are in AndroidTest we need Android Environment that's why [AndroidJUnit4]
 */
@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@HiltAndroidTest
class BookDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: BooksDatabase

    private lateinit var dao: BooksDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.booksDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertBook() = runBlockingTest {
        val book = Book(1, "name", "name", 1f, 1)
        dao.insert(book)

        val books: List<Book> = dao.getBooks().getOrAwaitValue()

        assertThat(books).contains(book)
    }

    @Test
    fun deleteBook() = runBlockingTest {
        val book = Book(1, "name", "name", 1f, 1)
        dao.insert(book)
        dao.delete(book)

        val books: List<Book> = dao.getBooks().getOrAwaitValue()

        assertThat(books).doesNotContain(book)
        assertThat(books).isEmpty()
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val book1 = Book(1, "name", "name", 10f, 2)
        val book2 = Book(2, "name", "name", 5.5f, 4)
        val book3 = Book(3, "name", "name", 100f, 0)

        dao.insert(book1)
        dao.insert(book2)
        dao.insert(book3)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(2 * 10f + 4 * 5.5f)

    }
}