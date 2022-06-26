package io.sharan.goodreads.framework

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.handleBackPress(action: () -> Unit) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            action()
            findNavController().popBackStack()
        }
    }

    requireActivity().onBackPressedDispatcher.addCallback(callback)
}