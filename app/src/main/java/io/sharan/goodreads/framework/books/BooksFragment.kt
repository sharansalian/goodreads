package io.sharan.goodreads.framework.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.R
import io.sharan.goodreads.databinding.FragmentBooksBinding
import io.sharan.goodreads.framework.BookListener
import io.sharan.goodreads.framework.BooksAdapter

@AndroidEntryPoint
class BooksFragment : Fragment() {

    val model: BooksViewModel by viewModels()

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

        val booksAdapter = BooksAdapter(BookListener {

        })

        binding.rvBooks.apply {
            adapter = booksAdapter
        }

        model.books.observe(viewLifecycleOwner) {
            booksAdapter.submitList(it)
        }
    }

}