package com.imannuel.simple_movie_app.data.source.remote.response.review

import com.google.gson.annotations.SerializedName

data class AuthorDetails(
    val name: String,

    val username: String,

    @SerializedName("avatar_path")
    val avatarPath: String?,

    val rating: Double?,
)