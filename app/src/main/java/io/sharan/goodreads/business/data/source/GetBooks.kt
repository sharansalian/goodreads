package io.sharan.goodreads.business.data.source

import io.sharan.goodreads.business.data.Book

interface GetBooks {
    fun getBooks(): List<Book>
}