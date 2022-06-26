package io.sharan.goodreads.framework.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.databinding.FragmentBooksBinding
import io.sharan.goodreads.framework.BookListener
import io.sharan.goodreads.framework.BooksAdapter
import io.sharan.goodreads.framework.ui.BooksViewModel

@AndroidEntryPoint
class BooksFragment : Fragment() {

    val booksViewModel: BooksViewModel by viewModels()

    lateinit var binding: FragmentBooksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBooksBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        booksViewModel.navigateToSleepDetail.observe(viewLifecycleOwner) {
            it?.let {
                this.findNavController().navigate(
                    BooksFragmentDirections.actionBooksFragmentToBookDetailFragment()
                )
                booksViewModel.onBookDetailNavigated()
            }
        }

        val booksAdapter = BooksAdapter(BookListener {
            booksViewModel.onBookClicked(it)
        })

        /**
         * RecyclerView doesn't know anything about your data or what type of layout each item has.
         * The LayoutManager arranges the items on the screen,
         * but the adapter adapts the data to be displayed and passes view holders to the RecyclerView.
         * So you will add the code to create headers in the adapter
         */
        binding.rvBooks.apply {
            adapter = booksAdapter
        }

        booksViewModel.books.observe(viewLifecycleOwner) {
            booksAdapter.addHeaderAndSubmitList(it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(BooksFragmentDirections.actionBooksFragmentToAddBookFragment())
        }
    }

}