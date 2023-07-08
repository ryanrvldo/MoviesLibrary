package com.ryanrvldo.movieslibrary.ui.genres

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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ryanrvldo.movieslibrary.databinding.FragmentGenreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GenreFragment : Fragment() {

    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GenreViewModel by viewModels()

    @Inject
    lateinit var adapter: GenreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is GenreUiState.Success -> adapter.submitList(uiState.news)
                        is GenreUiState.Error -> Toast.makeText(
                            context, uiState.message, Toast.LENGTH_SHORT
                        ).show()

                        is GenreUiState.Loading -> Toast.makeText(
                            context, "Loading...", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter.setOnNavigateToDetails {
            findNavController().navigate(
                GenreFragmentDirections.actionGenreToMovies(
                    it.id,
                    it.name
                )
            )
        }
        binding.rvGenre.layoutManager = GridLayoutManager(context, 2)
        binding.rvGenre.adapter = adapter
    }
}
