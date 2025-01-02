package com.imannuel.simple_movie_app.data.repository

import android.util.Log
import com.imannuel.simple_movie_app.data.source.remote.ApiServices
import com.imannuel.simple_movie_app.data.source.remote.response.genre.GenreList
import com.imannuel.simple_movie_app.utilities.exception.NoDataException
import com.imannuel.simple_movie_app.utilities.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException

class GenreRepository(
    private val apiServices: ApiServices
) {
    fun getMovieGenres(): Flow<NetworkResult<GenreList>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val movieGenres = apiServices.getMovieGenres()

                if (movieGenres.isSuccessful) {
                    emit(
                        NetworkResult.Success(
                            movieGenres.body() ?: throw NoDataException("No data found")
                        )
                    )
                } else {
                    movieGenres.errorBody()?.let {
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
                Log.e("GenreRepository", "HttpException: ${e.message}")
                emit(NetworkResult.Error("Request Failed: ${e.message.toString()}"))
            } catch (e: Exception) {
                Log.e("GenreRepository", "Exception: ${e.message}")
                emit(NetworkResult.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}