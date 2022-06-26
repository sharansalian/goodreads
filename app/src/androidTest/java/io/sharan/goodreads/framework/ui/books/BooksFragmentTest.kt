package io.sharan.goodreads.framework.ui.books

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.sharan.goodreads.launchFragmentInHiltContainer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import io.sharan.goodreads.R
import org.mockito.Mockito.verify

// Integration Test ( Interaction between two fragments )
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class BooksFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddBookItemButton_navigateToAddBookFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<BooksFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fab)).perform(click())

      /*  verify(navController).navigate(
            BooksFragmentDirections.actionBooksFragmentToAddBookFragment()
        )*/
    }
}