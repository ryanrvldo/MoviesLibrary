package com.ryanrvldo.movieslibrary.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest
import com.ryanrvldo.movieslibrary.BuildConfig
import com.ryanrvldo.movieslibrary.R
import com.ryanrvldo.movieslibrary.core.domain.model.Review
import com.ryanrvldo.movieslibrary.databinding.ItemReviewBinding
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MovieReviewsAdapter @Inject constructor() :
    PagingDataAdapter<Review, MovieReviewsAdapter.MovieReviewViewHolder>(REVIEW_COMPARATOR) {

    companion object {
        private val REVIEW_COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieReviewViewHolder(
        ItemReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MovieReviewViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Review) = with(binding) {
            tvAuthor.text = item.authorName
            tvDate.text = formatIsoDate(item.createdAt)
            tvRating.text = item.authorRating
            icStar.isVisible = item.authorRating.isNullOrBlank()
            tvRating.isVisible = item.authorRating.isNullOrBlank()
            if (tvRating.isVisible) {
                tvRating.text = item.authorRating
            }
            tvReview.text = item.review
            item.authorAvatarPath?.let { avatarPath ->
                itemView.context.apply {
                    imageLoader.enqueue(
                        ImageRequest.Builder(this)
                            .data(BuildConfig.TMDB_IMG_342_URL + avatarPath)
                            .crossfade(true)
                            .error(R.drawable.baseline_broken_image_24)
                            .placeholder(R.drawable.ic_person)
                            .target(binding.imgAvatar)
                            .build()
                    )
                }
            }
        }

        private fun formatIsoDate(isoDate: String): String {
            val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.UK)
            val resultDateFormat = SimpleDateFormat("d MMMM yyyy HH:mm:ss", Locale.getDefault())
            val date = isoDateFormat.parse(isoDate)
            return date?.let { resultDateFormat.format(it) } ?: "N/A"
        }
    }
}
