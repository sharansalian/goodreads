package io.sharan.goodreads.framework.ui.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
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
import org.mockito.Mockito.verify
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.ui.BookFragmentFactory
import io.sharan.goodreads.framework.ui.BooksViewModel
import io.sharan.goodreads.getOrAwaitValue
import io.sharan.goodreads.repositories.FakeBooksRepositoryImpl
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddBookFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: BookFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddBookFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickBookImage_navigateToImageFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddBookFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivBookImage)).perform(click())

        verify(navController).navigate(
            AddBookFragmentDirections.actionAddBookFragmentToImageFragment()
        )
    }

    @Test
    fun clickInsertIntoDb_bookInsertedIntoDb() {
        val testViewModel = BooksViewModel(FakeBooksRepositoryImpl())
        launchFragmentInHiltContainer<AddBookFragment>(fragmentFactory = fragmentFactory) {
            booksViewModel = testViewModel
        }

        onView(withId(R.id.etbookName)).perform(replaceText("Book"))
        onView(withId(R.id.etBookAmount)).perform(replaceText("5"))
        onView(withId(R.id.etBookPrice)).perform(replaceText("5.5"))

        onView(withId(R.id.btnAddBookItem)).perform(click())

        assertThat(testViewModel.books.getOrAwaitValue()).contains(
            Book(
                title = "Book",
                author = "JK",
                price = 5.5f,
                amount = 5,
            )
        )
    }
}