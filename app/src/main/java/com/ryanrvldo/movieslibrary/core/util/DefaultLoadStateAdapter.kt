package com.ryanrvldo.movieslibrary.core.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ryanrvldo.movieslibrary.databinding.LoadStateItemBinding
import javax.inject.Inject

class DefaultLoadStateAdapter @Inject constructor() :
    LoadStateAdapter<DefaultLoadStateAdapter.DefaultLoadStateViewHolder>() {

    private var onRetry: Function0<Unit>? = null

    fun setOnRetry(onRetry: () -> Unit) {
        this.onRetry = onRetry
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        DefaultLoadStateViewHolder(
            LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            this.onRetry ?: { }
        )

    override fun onBindViewHolder(holder: DefaultLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class DefaultLoadStateViewHolder(
        private val binding: LoadStateItemBinding,
        onRetry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { onRetry() }
        }

        fun bind(item: LoadState) = with(binding) {
            if (item is LoadState.Error) {
                tvErrorMessage.text = item.error.localizedMessage
            }
            progressBar.isVisible = item is LoadState.Loading
            btnRetry.isVisible = item is LoadState.Error
            tvErrorMessage.isVisible = item is LoadState.Error
        }

    }

}
