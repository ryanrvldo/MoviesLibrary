package com.ryanrvldo.movieslibrary.ui.movies

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
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryanrvldo.movieslibrary.core.util.DefaultLoadStateAdapter
import com.ryanrvldo.movieslibrary.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()

    private var _concatAdapter: ConcatAdapter? = null
    private val concatAdapter: ConcatAdapter
        get() = _concatAdapter!!

    @Inject
    lateinit var moviesPagingAdapter: MoviesAdapter

    @Inject
    lateinit var loadStateAdapter: DefaultLoadStateAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _concatAdapter = moviesPagingAdapter.withLoadStateFooter(loadStateAdapter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnRefreshListener { moviesPagingAdapter.refresh() }
        loadStateAdapter.setOnRetry(moviesPagingAdapter::retry)
        binding.rvMovies.layoutManager = LinearLayoutManager(context)
        binding.rvMovies.adapter = concatAdapter
        addPagingAdapterStateListener()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    when (uiState) {
                        is MoviesUiState.Success -> moviesPagingAdapter.submitData(uiState.moviePagingData)

                        is MoviesUiState.Error -> Toast.makeText(
                            context, uiState.message, Toast.LENGTH_SHORT
                        ).show()

                        MoviesUiState.Loading -> binding.root.isRefreshing = true
                    }
                }

            }
        }
    }

    private fun addPagingAdapterStateListener() {
        moviesPagingAdapter.addLoadStateListener { loadStates ->
            binding.rvMovies.isVisible = loadStates.refresh !is LoadState.Loading
            binding.root.isRefreshing = loadStates.refresh is LoadState.Loading
            when {
                loadStates.source.refresh is LoadState.NotLoading && loadStates.append.endOfPaginationReached && moviesPagingAdapter.itemCount < 1 -> {
                    binding.root.isRefreshing = false
                }

                loadStates.source.refresh is LoadState.NotLoading || loadStates.refresh is LoadState.Error -> {
                    binding.root.isRefreshing = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
