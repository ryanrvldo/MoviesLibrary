package com.ryanrvldo.movieslibrary.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import com.ryanrvldo.movieslibrary.databinding.ItemGenreBinding
import javax.inject.Inject

class GenreAdapter @Inject constructor() : ListAdapter<Genre, GenreAdapter.GenreViewHolder>(
    object : DiffUtil.ItemCallback<Genre?>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return (oldItem.id == newItem.id) && (oldItem.name == newItem.name)
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = GenreViewHolder(
        ItemGenreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GenreViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Genre) {
            binding.tvName.text = item.name
        }
    }
}