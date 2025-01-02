package com.imannuel.simple_movie_app.data.source.remote.response.movie.list

import com.google.gson.annotations.SerializedName

data class MovieList(
    val page: Long,

    val results: List<Movie>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long,
)