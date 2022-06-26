package io.sharan.goodreads.framework.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.databinding.FragmentAddBookBinding
import io.sharan.goodreads.framework.ui.BooksViewModel
import io.sharan.goodreads.framework.handleBackPress
import javax.inject.Inject

@AndroidEntryPoint
class AddBookFragment @Inject constructor(private val glide: RequestManager) : Fragment() {

    val booksViewModel: BooksViewModel by viewModels()

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

        binding.ivBookImage.setOnClickListener {
            findNavController().navigate(AddBookFragmentDirections.actionAddBookFragmentToImageFragment())
        }

        //Find extension in UEFA
        handleBackPress {
            booksViewModel.setCurImageUrl("")
        }
    }
}
