package io.sharan.goodreads.framework.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import io.sharan.goodreads.framework.adapters.BookAdapter
import io.sharan.goodreads.framework.adapters.ImageAdapter
import io.sharan.goodreads.framework.ui.add.AddBookFragment
import io.sharan.goodreads.framework.ui.books.BooksFragment
import io.sharan.goodreads.framework.ui.image.ImageFragment
import io.sharan.goodreads.repositories.FakeBooksRepositoryAndroidTest
import javax.inject.Inject

class TestBookFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val bookAdapter: BookAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageFragment::class.java.name -> ImageFragment(imageAdapter)
            AddBookFragment::class.java.name -> AddBookFragment(glide)
            BooksFragment::class.java.name -> BooksFragment(bookAdapter, viewModel = BooksViewModel(FakeBooksRepositoryAndroidTest()))
            else -> super.instantiate(classLoader, className)
        }

    }

}