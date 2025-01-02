package com.imannuel.simple_movie_app.data.source.remote

import com.imannuel.simple_movie_app.data.source.remote.response.genre.GenreList
import com.imannuel.simple_movie_app.data.source.remote.response.movie.detail.MovieDetail
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.MovieList
import com.imannuel.simple_movie_app.data.source.remote.response.review.ReviewList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    // get movie genre
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): Response<GenreList>

    // find movie with genres
    @GET("discover/movie")
    suspend fun discoverMovieByGenre(
        @Query("page") page: Int,
        @Query("with_genres") genres: String
    ): Response<MovieList>

    // get movie detail
    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: String,
        @Query("append_to_response") appendToResponse: String
    ): Response<MovieDetail>

    // get movie reviews
    @GET("movie/{id}/reviews")
    suspend fun getMovieReviews(
        @Path("id") id: String,
    ): Response<ReviewList>
}