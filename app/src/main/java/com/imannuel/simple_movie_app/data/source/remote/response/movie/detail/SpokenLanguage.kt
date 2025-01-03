package com.imannuel.simple_movie_app.data.source.remote.response.movie.detail

import com.google.gson.annotations.SerializedName


data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,

    @SerializedName("iso_639_1")
    val iso6391: String,

    val name: String,
)
