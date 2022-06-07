package io.sharan.goodreads.framework

import androidx.lifecycle.ViewModel
import io.sharan.goodreads.business.data.Book

class MainViewModel : ViewModel() {

    val books = listOf<Book>(Book(id = 1, "The Wizard of Once", "Cressida Cowell"))
}