package com.example.kiantest.api

import com.example.kiantest.data.MovieItem

data class MovieListResponse(
    val results : List<MovieItem>
)