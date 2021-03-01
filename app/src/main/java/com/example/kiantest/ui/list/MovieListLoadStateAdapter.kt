package com.example.kiantest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kiantest.databinding.MovieListLoadStateFooterBinding

class MovieListLoadStateAdapter (private val retry: ()->Unit) :
    LoadStateAdapter<MovieListLoadStateAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            MovieListLoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: MovieListLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.fragmentMovieListRetryBtn.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                fragmentMovieListProgressbar.isVisible = loadState is LoadState.Loading
                fragmentMovieListRetryBtn.isVisible = loadState !is LoadState.Loading
                fragmentMovieListRetryText.isVisible = loadState !is LoadState.Loading
            }
        }
    }


}