package io.sharan.goodreads.framework.ui.image

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.databinding.FragmentImageBinding
import io.sharan.goodreads.framework.adapters.ImageAdapter
import io.sharan.goodreads.framework.common.BaseFragmentViewBinding
import io.sharan.goodreads.framework.other.Constants
import io.sharan.goodreads.framework.ui.BooksViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ImageFragment @Inject constructor(val imageAdapter: ImageAdapter) :
    BaseFragmentViewBinding<FragmentImageBinding>(FragmentImageBinding::inflate) {

    lateinit var viewModel: BooksViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BooksViewModel::class.java]
        setUpRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), Constants.GRID_SPAN_COUNT)
        }
    }
}