package com.imannuel.simple_movie_app.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.imannuel.simple_movie_app.data.source.remote.ApiServices
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.Movie

private const val INITIAL_PAGE_INDEX = 1

class MoviePagingSource(
    private val apiServices: ApiServices,
    private val genre: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: INITIAL_PAGE_INDEX

        return try {
            val responseData = apiServices.discoverMovieByGenre(position, genre)

            val movies = responseData.body()?.results ?: emptyList()

            LoadResult.Page(
                data = movies,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}
