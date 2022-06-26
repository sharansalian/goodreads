package io.sharan.goodreads.framework.ui.books

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.sharan.goodreads.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.adapters.BookAdapter
import io.sharan.goodreads.framework.ui.BooksViewModel
import io.sharan.goodreads.framework.ui.TestBookFragmentFactory
import io.sharan.goodreads.getOrAwaitValue
import org.mockito.Mockito.verify
import javax.inject.Inject

// Integration Test ( Interaction between two fragments )
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class BooksFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testBookFragmentFactory: TestBookFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun swipeBook_deleteItemInDb() {
        val book = Book(
            title = "book",
            amount = 1,
            price = 4f,
            id = 1,
            curImageUrl = "",
            author = "JK"
        )
        var testViewModel: BooksViewModel? = null

        launchFragmentInHiltContainer<BooksFragment>(fragmentFactory = testBookFragmentFactory) {
            testViewModel = viewModel
            viewModel?.insertBook(book)
        }

        onView(withId(R.id.rvBooks)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BookAdapter.BookItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.books?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddBookItemButton_navigateToAddBookFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<BooksFragment>(fragmentFactory = testBookFragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fab)).perform(click())

        verify(navController).navigate(
            BooksFragmentDirections.actionBooksFragmentToAddBookFragment()
        )
    }
}