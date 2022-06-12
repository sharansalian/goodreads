package io.sharan.goodreads.framework.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.sharan.goodreads.MainCoroutineRule
import io.sharan.goodreads.framework.other.Constants
import io.sharan.goodreads.framework.other.Status
import io.sharan.goodreads.getOrAwaitValue
import io.sharan.goodreads.repositories.FakeBooksRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class BooksViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /***
     * Error IllegalStateException: Module with the Main dispatcher had failed to initialize.
     * As we use coroutine for db transaction that uses Main dispatcher we don't have that in test environment
     */

    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BooksViewModel

    @Before
    fun setUp() {
        viewModel = BooksViewModel(FakeBooksRepositoryImpl())
    }

    @Test
    fun `insert book with empty field, returns error`() {
        viewModel.validateBook("name", "", "3.0")

        val value = viewModel.insertBookStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert book with too long name, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.validateBook(string, "5", "3.0")

        val value = viewModel.insertBookStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert book with too long price, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.validateBook("name", "5", string)

        val value = viewModel.insertBookStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert book with too high amount, returns error`() {
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.validateBook("name", "99999999999999999", string)

        val value = viewModel.insertBookStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }


    @Test
    fun `insert book with valid input, returns success`() {
        viewModel.validateBook("name", "99", "10")

        val value = viewModel.insertBookStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}
