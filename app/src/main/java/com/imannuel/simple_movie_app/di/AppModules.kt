package com.imannuel.simple_movie_app.di

import com.imannuel.simple_movie_app.data.repository.GenreRepository
import com.imannuel.simple_movie_app.data.repository.MovieRepository
import com.imannuel.simple_movie_app.data.source.remote.ApiConfig
import com.imannuel.simple_movie_app.ui.home.HomeViewModel
import com.imannuel.simple_movie_app.ui.movie.discover.DiscoverMovieViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val apiModule = module {
    single { ApiConfig().getApiService() }
}

val repositoryModule = module {
    single { GenreRepository(get()) }
    single { MovieRepository(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DiscoverMovieViewModel(get()) }
}