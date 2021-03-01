package com.example.kiantest.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.kiantest.R
import com.example.kiantest.data.MovieItem
import com.example.kiantest.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListAdapter.clickListener {

    private val viewModel by viewModels<MovieListViewModel>()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieListBinding.bind(view)

        val adapter = MovieListAdapter(this)

        binding.apply {
            fragmentMovieListRecyclerview.setHasFixedSize(true)
            fragmentMovieListRecyclerview.itemAnimator = null
            fragmentMovieListRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieListLoadStateAdapter { adapter.retry() },
                footer = MovieListLoadStateAdapter { adapter.retry() }
            )
            fragmentMovieListRetryBtn.setOnClickListener {
                adapter.retry()
            }

        }

        viewModel.movielist.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadstate ->
            binding.apply {
                fragmentMovieListProgressbar.isVisible =
                    loadstate.source.refresh is LoadState.Loading
                fragmentMovieListRecyclerview.isVisible =
                    loadstate.source.refresh is LoadState.NotLoading
                fragmentMovieListRetryBtn.isVisible = loadstate.source.refresh is LoadState.Error
                fragmentMovieListRetryText.isVisible = loadstate.source.refresh is LoadState.Error

                // when the search result is empty
                if (loadstate.source.refresh is LoadState.NotLoading &&
                    loadstate.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    fragmentMovieListRecyclerview.isVisible = false
                    fragmentMovieListNoResults.isVisible = true
                } else {
                    fragmentMovieListNoResults.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)

    }

    override fun onClickListener(movie: MovieItem) {
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movie)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.movies_menu, menu)

        val searchItem = menu.findItem(R.id.search_menu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.fragmentMovieListRecyclerview.scrollToPosition(0)
                    //the search return the movies in the exact date, not in a range.
                    //if we want to see it in a range we can declare two fields
                    viewModel.searchMovieList(query, query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}