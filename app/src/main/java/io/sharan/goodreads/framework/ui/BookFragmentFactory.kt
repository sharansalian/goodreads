package io.sharan.goodreads.framework.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import io.sharan.goodreads.framework.adapters.ImageAdapter
import io.sharan.goodreads.framework.ui.add.AddBookFragment
import io.sharan.goodreads.framework.ui.image.ImageFragment
import javax.inject.Inject

class BookFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ImageFragment::class.java.name -> ImageFragment(imageAdapter)
            AddBookFragment::class.java.name -> AddBookFragment(glide)
            else -> super.instantiate(classLoader, className)
        }

    }

}