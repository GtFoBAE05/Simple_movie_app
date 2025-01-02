package com.imannuel.simple_movie_app.data.source.remote.response.video

import com.google.gson.annotations.SerializedName


data class VideoList(
    @SerializedName("results")
    val videos: List<Video>,
)