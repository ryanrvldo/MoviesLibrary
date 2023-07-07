package com.ryanrvldo.movieslibrary.ui.genres

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryanrvldo.movieslibrary.databinding.ActivityGenreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GenreActivity : AppCompatActivity() {

    private val viewModel: GenreViewModel by viewModels()

    @Inject
    lateinit var adapter: GenreAdapter

    private lateinit var _binding: ActivityGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setupRecyclerView()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is GenreUiState.Success -> adapter.submitList(uiState.news)
                        is GenreUiState.Error -> Toast.makeText(
                            this@GenreActivity,
                            uiState.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        is GenreUiState.Loading -> Toast.makeText(
                            this@GenreActivity,
                            "Loading...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        _binding.rvGenre.layoutManager = LinearLayoutManager(this)
        _binding.rvGenre.adapter = adapter
    }
}