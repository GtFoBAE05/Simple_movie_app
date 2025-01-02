package com.imannuel.simple_movie_app.ui.movie.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imannuel.simple_movie_app.data.source.remote.response.movie.list.Movie
import com.imannuel.simple_movie_app.databinding.MovieCardBinding

class DiscoverMovieAdapter(private val listener: (Movie) -> Unit) :
    PagingDataAdapter<Movie, DiscoverMovieAdapter.DiscoverMovieViewHolder>(DIFF_CALLBACK) {

    class DiscoverMovieViewHolder(private val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500" + item.posterPath)
                .into(binding.movieCardImageView)

            binding.movieCardTitleTextView.text = item.title
            binding.movieCardReleaseDateTextView.text = item.releaseDate
            binding.movieCardOverviewTextView.text = item.overview
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder {
        return DiscoverMovieViewHolder(
            MovieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) {
        val movie = getItem(position)

        if (movie != null) {
            holder.bind(movie)
            holder.itemView.setOnClickListener {
                listener(movie)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
