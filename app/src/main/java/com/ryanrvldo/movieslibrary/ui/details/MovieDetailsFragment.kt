package com.ryanrvldo.movieslibrary.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.imageLoader
import coil.request.ImageRequest
import com.ryanrvldo.movieslibrary.BuildConfig
import com.ryanrvldo.movieslibrary.R
import com.ryanrvldo.movieslibrary.core.domain.model.Genre
import com.ryanrvldo.movieslibrary.core.domain.model.MovieDetails
import com.ryanrvldo.movieslibrary.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailsViewModel by viewModels()

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    when (uiState) {
                        is MovieDetailsUiState.Success -> renderData(uiState.movieDetails)
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
    private fun renderData(movieDetails: MovieDetails) = with(binding) {
        tvTitle.text = movieDetails.title
        tvReleaseDate.text = movieDetails.releaseDate
        tvRuntime.text = getRuntimeDuration(movieDetails.runtime)
        tvGenres.text = movieDetails.genres.map(Genre::name).joinToString()
        tvRating.text = movieDetails.voteAverage.toString()
        tvVoteRating.text = "(${movieDetails.voteCount})"
        tvOverview.text = movieDetails.overview
        requireContext().imageLoader.enqueue(
            ImageRequest.Builder(requireContext())
                .data(BuildConfig.TMDB_IMG_500_BASE_URL + movieDetails.posterPath)
                .crossfade(true)
                .error(R.drawable.baseline_broken_image_24)
                .target(imgPoster)
                .build()
        )
    }

    private fun getRuntimeDuration(runtime: Int): String {
        val hour = runtime / 60
        val minute = runtime % 60
        return String.format(Locale.US, "%dh %02d min", hour, minute)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
