package io.sharan.goodreads.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sharan.goodreads.R
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val model: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val booksAdapter = BooksAdapter()

        binding.rvBooks.apply {
            adapter = booksAdapter
        }

        model.books.observe(this) {
            it?.let {
                booksAdapter.submitList(it)
            }
        }


    }
}