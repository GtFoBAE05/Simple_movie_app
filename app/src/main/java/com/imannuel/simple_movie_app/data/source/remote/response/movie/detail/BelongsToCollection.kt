package com.imannuel.simple_movie_app.data.source.remote.response.movie.detail

import com.google.gson.annotations.SerializedName

data class BelongsToCollection(
    val id: Long,

    val name: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,
)
