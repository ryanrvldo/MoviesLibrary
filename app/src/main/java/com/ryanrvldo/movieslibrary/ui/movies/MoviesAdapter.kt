package com.ryanrvldo.movieslibrary.ui.movies

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.ryanrvldo.movieslibrary.BuildConfig
import com.ryanrvldo.movieslibrary.R
import com.ryanrvldo.movieslibrary.core.domain.model.Movie
import com.ryanrvldo.movieslibrary.databinding.ItemMovieBinding
import javax.inject.Inject

class MoviesAdapter @Inject constructor() :
    PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onNavigateToDetails: Function1<Movie, Unit>? = null

    fun setOnNavigateToDetails(onNavigate: (Movie) -> Unit) {
        onNavigateToDetails = onNavigate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MoviesViewHolder(
        ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MoviesViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Movie) = with(binding) {
            tvTitle.text = item.title
            tvOverview.text = item.overview
            tvRating.text = item.voteAverage.toString()
            tvVoteRating.text = "(${item.voteCount})"
            tvReleaseDate.text = item.releaseDate
            itemView.context.imageLoader.enqueue(
                ImageRequest.Builder(itemView.context)
                    .data(BuildConfig.TMDB_IMG_500_URL + item.backdropPath)
                    .crossfade(true)
                    .error(R.drawable.baseline_broken_image_24)
                    .target(imgBackdrop)
                    .build()
            )

            root.setOnClickListener {
                onNavigateToDetails?.invoke(item)
            }
        }
    }
}
