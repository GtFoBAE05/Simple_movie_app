package com.imannuel.simple_movie_app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imannuel.simple_movie_app.data.source.remote.response.genre.Genre
import com.imannuel.simple_movie_app.databinding.GenresCardBinding
import com.imannuel.simple_movie_app.utilities.helper.DiffUtil

class GenresAdapter(private val listener: (Genre) -> Unit) :
    RecyclerView.Adapter<GenresAdapter.GenresViewHolder>() {

    private var items = emptyList<Genre>()

    class GenresViewHolder(private val binding: GenresCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            binding.genreCardTitleTv.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        return GenresViewHolder(
            GenresCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    fun setData(data: List<Genre>) {
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }


}