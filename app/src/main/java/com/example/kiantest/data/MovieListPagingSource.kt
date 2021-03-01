package com.example.kiantest.data

import androidx.paging.PagingSource
import com.example.kiantest.BuildConfig
import com.example.kiantest.api.MovieListApi
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_LIST_STARTING_PAGE_INDEX = 1

class MovieListPagingSource(
    private val movieListApi: MovieListApi,
    private val release_date_gte: String?,
    private val release_date_lte: String?
) : PagingSource<Int, MovieItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val position = params.key ?: MOVIE_LIST_STARTING_PAGE_INDEX

        return try {

            val response = movieListApi.searchByDate(BuildConfig.api_keyy,  position, release_date_gte, release_date_lte)
            val movieList = response.results

            LoadResult.Page(
                data = movieList,
                prevKey = if (position == MOVIE_LIST_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movieList.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }

    }


}