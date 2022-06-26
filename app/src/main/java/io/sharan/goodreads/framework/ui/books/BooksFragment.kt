package io.sharan.goodreads.framework.ui.books

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.databinding.FragmentBooksBinding
import io.sharan.goodreads.framework.adapters.BookAdapter
import io.sharan.goodreads.framework.common.BaseFragmentViewBinding
import io.sharan.goodreads.framework.ui.BooksViewModel
import javax.inject.Inject

@AndroidEntryPoint
class BooksFragment @Inject constructor(val bookAdapter: BookAdapter, var viewModel: BooksViewModel? = null) :
    BaseFragmentViewBinding<FragmentBooksBinding>(FragmentBooksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(BooksViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()

        viewModel?.navigateToSleepDetail?.observe(viewLifecycleOwner) {
            it?.let {
                this.findNavController().navigate(
                    BooksFragmentDirections.actionBooksFragmentToBookDetailFragment()
                )
                viewModel?.onBookDetailNavigated()
            }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(BooksFragmentDirections.actionBooksFragmentToAddBookFragment())
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = bookAdapter.books[position]
            viewModel?.deleteBook(item)
            Snackbar.make(
                requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo") {
                    viewModel?.insertBook(item)
                }
            }.show()
        }
    }

    private fun subscribeToObservers() {
        viewModel?.books?.observe(viewLifecycleOwner) {
            bookAdapter.books = it
        }

        viewModel?.totalPrice?.observe(viewLifecycleOwner) {
            val price = it ?: 0.0f
            val priceText = "Total Price: $price€"
            binding.tvTotalPrice.text = priceText
        }
    }

    /**
     * RecyclerView doesn't know anything about your data or what type of layout each item has.
     * The LayoutManager arranges the items on the screen,
     * but the adapter adapts the data to be displayed and passes view holders to the RecyclerView.
     * So you will add the code to create headers in the adapter
     */
    private fun setupRecyclerView() {
        binding.rvBooks.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)

        }
    }

}