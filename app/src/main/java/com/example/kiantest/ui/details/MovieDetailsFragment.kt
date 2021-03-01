package com.example.kiantest.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.kiantest.R
import com.example.kiantest.databinding.FragmentDetailsMovieBinding

class MovieDetailsFragment : Fragment(R.layout.fragment_details_movie) {

    private val args by navArgs<MovieDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsMovieBinding.bind(view)

        binding.apply {
            val movie = args.movie

            Glide.with(this@MovieDetailsFragment)
                .load(movie.image_url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                       progressBarDetailsFragment.isVisible= false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBarDetailsFragment.isVisible= false
                        return false
                    }

                })
                .into(fragmentDetailsMovieImg)

            fragmentDetailsMovieDescrption.text = movie.overview
            fragmentDetailsMovieTitle.text = movie.original_title
            fragmentDetailsMovieDate.text = movie.release_date

        }
    }

}