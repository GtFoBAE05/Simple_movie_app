package com.imannuel.simple_movie_app.data.source.remote.response.review

import com.google.gson.annotations.SerializedName

data class ReviewList(
    val id: Long,

    val page: Long,

    @SerializedName("results")
    val reviews: List<ReviewList>,

    @SerializedName("total_pages")
    val totalPages: Long,

    @SerializedName("total_results")
    val totalResults: Long,
)