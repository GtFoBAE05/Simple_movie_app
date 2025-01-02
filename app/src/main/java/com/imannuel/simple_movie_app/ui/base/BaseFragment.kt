package com.imannuel.simple_movie_app.ui.base

import androidx.fragment.app.Fragment
import com.imannuel.simple_movie_app.utilities.extension.showToast
import com.imannuel.simple_movie_app.utilities.network.NetworkUtils.isNetworkAvailable

open class BaseFragment : Fragment() {
    protected fun checkNetworkConnection(action: () -> Unit) {
        if (requireContext().isNetworkAvailable()) {
            action()
        } else {
            showToast("No internet connection")
        }
    }
}