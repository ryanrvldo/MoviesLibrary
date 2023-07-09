package com.ryanrvldo.movieslibrary.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.imageLoader
import coil.request.ImageRequest
import com.ryanrvldo.movieslibrary.BuildConfig
import com.ryanrvldo.movieslibrary.R
import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.core.domain.model.Video
import com.ryanrvldo.movieslibrary.core.util.DefaultLoadStateAdapter
import com.ryanrvldo.movieslibrary.databinding.FragmentMovieDetailsBinding
import com.ryanrvldo.movieslibrary.databinding.ItemTrailerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()

    @Inject
    lateinit var reviewPagingAdapter: MovieReviewsAdapter

    @Inject
    lateinit var loadStateAdapter: DefaultLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupReviewSection()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    when (uiState) {
                        is MovieDetailsUiState.Success -> {
                            renderMovieData(uiState.movieDetails)
                            reviewPagingAdapter.submitData(uiState.reviewPagingData)
                        }

                        is MovieDetailsUiState.Error -> Toast.makeText(
                            context,
                            uiState.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        is MovieDetailsUiState.Loading -> Toast.makeText(
                            context,
                            "Loading...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun renderMovieData(movieDetails: MovieDetails) = with(binding) {
        tvTitle.text = movieDetails.title
        tvReleaseDate.text = movieDetails.releaseDate
        tvRuntime.text = getRuntimeDuration(movieDetails.runtime)
        tvGenres.text = movieDetails.genres.map(Genre::name).joinToString()
        tvRating.text = movieDetails.voteAverage.toString()
        tvVoteRating.text = "(${movieDetails.voteCount})"
        tvOverview.text = movieDetails.overview
        requireContext().imageLoader.enqueue(
            ImageRequest.Builder(requireContext())
                .data(BuildConfig.TMDB_IMG_342_URL + movieDetails.posterPath)
                .crossfade(true)
                .error(R.drawable.baseline_broken_image_24)
                .target(imgPoster)
                .build()
        )
        setupMovieTrailerVideos(movieDetails.videos)
    }

    private fun setupMovieTrailerVideos(videos: List<Video>) {
        if (videos.isEmpty()) {
            binding.lblTrailers.isVisible = false
            binding.trailerScrollView.isVisible = false
            return
        }

        binding.trailerLinearLayout.removeAllViews()
        for (video in videos) {
            val itemTrailerBinding = ItemTrailerBinding.inflate(
                LayoutInflater.from(context),
                binding.trailerLinearLayout,
                false
            )
            itemTrailerBinding.tvTitle.text = video.name
            context?.let {
                it.imageLoader.enqueue(
                    ImageRequest.Builder(it)
                        .data(String.format(BuildConfig.YOUTUBE_THUMBNAIL_URL, video.key))
                        .crossfade(true)
                        .error(R.drawable.baseline_broken_image_24)
                        .target(itemTrailerBinding.imgThumbnail)
                        .build()
                )
                itemTrailerBinding.imgThumbnail.requestLayout()
                itemTrailerBinding.imgThumbnail.setOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(String.format(BuildConfig.YOUTUBE_VIDEO_URL, video.key))
                        )
                    )
                }
            }
            binding.trailerLinearLayout.addView(itemTrailerBinding.root)
        }
    }

    private fun getRuntimeDuration(runtime: Int): String {
        val hour = runtime / 60
        val minute = runtime % 60
        return String.format(Locale.US, "%dh %02d min", hour, minute)
    }

    private fun setupReviewSection() {
        loadStateAdapter.setOnRetry(reviewPagingAdapter::retry)
        binding.rvReviews.layoutManager = LinearLayoutManager(context)
        binding.rvReviews.adapter = reviewPagingAdapter.withLoadStateFooter(loadStateAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
