package io.sharan.goodreads.framework.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sharan.goodreads.business.data.Book
import io.sharan.goodreads.framework.data.remote.responses.ImageResponse
import io.sharan.goodreads.framework.other.Constants
import io.sharan.goodreads.framework.other.Event
import io.sharan.goodreads.framework.other.Resource
import io.sharan.goodreads.framework.repositories.BooksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {

    private val _navigateToSleepDetail = MutableLiveData<Int?>()
    val navigateToSleepDetail
        get() = _navigateToSleepDetail

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertBookStatus = MutableLiveData<Event<Resource<Book>>>()
    val insertBookStatus: LiveData<Event<Resource<Book>>> = _insertBookStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.value = url
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        repository.deleteBook(book)
    }

    fun insertBook(book: Book) = viewModelScope.launch {
        repository.insertBook(book)
    }

    fun validateBook(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertBookStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }

        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertBookStatus.postValue(
                Event(
                    Resource.error(
                        "The name of the item must " +
                                "not exceed ${Constants.MAX_NAME_LENGTH}", null
                    )
                )
            )
            return
        }

        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertBookStatus.postValue(
                Event(
                    Resource.error(
                        "The price of the item must " +
                                "not exceed ${Constants.MAX_PRICE_LENGTH}", null
                    )
                )
            )
            return
        }

        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertBookStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }

        val book = Book(
            title = name,
            author = "JK",
            price = priceString.toFloat(),
            amount = amount,
            curImageUrl = _curImageUrl.value ?: ""
        )

        insertBook(book)
        setCurImageUrl("")
        _insertBookStatus.postValue(Event(Resource.success(book)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()) {
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

    val books = repository.observeAllBooks()

    val totalPrice = repository.observeTotalPrice()

    fun onBookClicked(id: Int) {
        _navigateToSleepDetail.value = id
    }

    fun onBookDetailNavigated() {
        _navigateToSleepDetail.value = null
    }

}

/**
 *  Livedata value vs postValue: The value will notified on each update whereas
 *  post value done in short time will only emit change for the last value
 */