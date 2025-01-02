package com.imannuel.simple_movie_app.data.source.remote.response.movie.detail

import com.google.gson.annotations.SerializedName


data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,

    val name: String,
)
