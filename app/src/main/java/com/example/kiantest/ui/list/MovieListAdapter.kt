package com.example.kiantest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kiantest.R
import com.example.kiantest.data.MovieItem
import com.example.kiantest.databinding.MovieListItemsBinding

class MovieListAdapter (private val listener: clickListener):
    PagingDataAdapter<MovieItem, MovieListAdapter.MovieViewHolder>(
        PHOTO_COMPARATOR
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MovieViewHolder(private val binding: MovieListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    if (item != null){
                        listener.onClickListener(item)
                    }
                }
            }
        }
        fun bind(movieItem: MovieItem) {

            binding.apply {
                Glide.with(itemView)
                    .load(movieItem.image_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.error)
                    .into(movieListItemsImg)

                movieListItemsTitle.text = movieItem.original_title
                movieListItemsDate.text = movieItem.release_date
            }
        }

    }

    interface clickListener{
        fun onClickListener(movie : MovieItem)
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem) =
                oldItem == newItem
        }
    }


}