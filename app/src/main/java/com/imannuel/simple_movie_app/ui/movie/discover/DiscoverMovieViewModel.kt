package com.imannuel.simple_movie_app.ui.movie.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.imannuel.simple_movie_app.data.repository.MovieRepository
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.Movie
import kotlinx.coroutines.flow.Flow

class DiscoverMovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun discoverMovieByGenre(genres: String): Flow<PagingData<Movie>> {
        return movieRepository.discoverMovieByGenre(genres)
            .cachedIn(viewModelScope)
    }
}