package io.sharan.goodreads.business.interactors

import io.sharan.goodreads.business.data.Book

class GetBooks(private val getBooks: io.sharan.goodreads.business.data.source.GetBooks) {
    operator fun invoke(): List<Book> {
        return getBooks.getBooks()
    }
}