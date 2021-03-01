package com.example.kiantest.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.kiantest.api.MovieListApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDBRepositiry  @Inject constructor(private val movieListApi: MovieListApi) {

    fun getSearchResults (release_date_gte: String? , release_date_lte: String?) = Pager (
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {MovieListPagingSource(movieListApi,release_date_gte, release_date_lte )}
    ).liveData
}