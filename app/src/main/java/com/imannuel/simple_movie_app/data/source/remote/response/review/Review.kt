package com.imannuel.simple_movie_app.data.source.remote.response.review

import com.google.gson.annotations.SerializedName

data class Review(
    val author: String,

    @SerializedName("author_details")
    val authorDetails: AuthorDetails,

    val content: String,

    @SerializedName("created_at")
    val createdAt: String,

    val id: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    val url: String,
)