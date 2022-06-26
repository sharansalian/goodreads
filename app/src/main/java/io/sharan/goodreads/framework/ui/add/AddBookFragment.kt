package io.sharan.goodreads.framework.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.databinding.FragmentAddBookBinding
import io.sharan.goodreads.framework.ui.BooksViewModel
import io.sharan.goodreads.framework.handleBackPress
import io.sharan.goodreads.framework.other.Status
import javax.inject.Inject

@AndroidEntryPoint
class AddBookFragment @Inject constructor(private val glide: RequestManager) : Fragment() {

    lateinit var booksViewModel: BooksViewModel

    lateinit var binding: FragmentAddBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddBookBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        booksViewModel = ViewModelProvider(requireActivity())[BooksViewModel::class.java]

        binding.ivBookImage.setOnClickListener {
            findNavController().navigate(AddBookFragmentDirections.actionAddBookFragmentToImageFragment())
        }

        handleBackPress {
            booksViewModel.setCurImageUrl("")
        }

        binding.btnAddBookItem.setOnClickListener {
            booksViewModel.validateBook(
                name = binding.etbookName.text.toString(),
                amountString = binding.etBookAmount.text.toString(),
                priceString = binding.etBookPrice.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        booksViewModel.curImageUrl.observe(viewLifecycleOwner) {
            glide.load(it).into(binding.ivBookImage)
        }

        booksViewModel.insertBookStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        Snackbar.make(
                            requireView(),
                            "Add Shopping Item",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireView(),
                            result.message ?: "Unknown error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> Unit
                }
            }
        }
    }
}
