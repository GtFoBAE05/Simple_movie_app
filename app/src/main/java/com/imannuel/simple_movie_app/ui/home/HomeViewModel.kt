package com.imannuel.simple_movie_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imannuel.simple_movie_app.data.repository.GenreRepository
import com.imannuel.simple_movie_app.data.source.remote.response.genre.GenreList
import com.imannuel.simple_movie_app.utilities.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {
    private val _genreState = MutableStateFlow<NetworkResult<GenreList>>(NetworkResult.Loading)
    val genreState: StateFlow<NetworkResult<GenreList>> = _genreState

    init {
        viewModelScope.launch {
            loadMovieGenres()
        }
    }

    fun loadMovieGenres() {
        viewModelScope.launch {
            genreRepository.getMovieGenres()
                .collect {
                    _genreState.value = it
                }
        }
    }
}