package com.imannuel.simple_movie_app.utilities.helper

import androidx.recyclerview.widget.DiffUtil
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}
