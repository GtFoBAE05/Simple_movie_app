package com.imannuel.simple_movie_app.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.imannuel.simple_movie_app.data.source.remote.ApiServices
import com.imannuel.simple_movie_app.data.source.remote.paging.MoviePagingSource
import com.imannuel.simple_movie_app.data.source.remote.response.movie.detail.MovieDetail
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.Movie
import com.imannuel.simple_movie_app.utilities.exception.NoDataException
import com.imannuel.simple_movie_app.utilities.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class MovieRepository(private val apiServices: ApiServices) {
    fun discoverMovieByGenre(
        genres: String
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
            ),
            pagingSourceFactory = { MoviePagingSource(apiServices, genres) }
        ).flow
    }

    fun getMovieDetail(
        movieId: String
    ): Flow<NetworkResult<MovieDetail>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val movieDetail = apiServices.getMovieDetail(movieId, "videos")

                if (movieDetail.isSuccessful) {
                    emit(
                        NetworkResult.Success(
                            movieDetail.body() ?: throw NoDataException("No data found")
                        )
                    )
                } else {
                    movieDetail.errorBody()?.let {
                        try {
                            val error = JSONObject(it.string())
                            val errorMessage = error.optString("status_message", "Unknown error")
                            emit(NetworkResult.Error(errorMessage))
                        } catch (jsonException: Exception) {
                            emit(NetworkResult.Error("Error parsing error response: ${jsonException.message}"))
                        }
                    } ?: emit(NetworkResult.Error("Unknown error"))
                }
            } catch (e: HttpException) {
                Log.e("MovieRepository", "HttpException: ${e.message}")
                emit(NetworkResult.Error("Request Failed: ${e.message.toString()}"))
            } catch (e: Exception) {
                Log.e("MovieRepository", "Exception: ${e.message}")
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}