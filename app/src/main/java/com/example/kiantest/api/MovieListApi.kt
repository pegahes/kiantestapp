package com.example.kiantest.api

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieListApi {

    companion object {
        const val base_url = "https://api.themoviedb.org/3/"
    }


    //@Headers("Authorization: Bearer $tmd_api_key", "Content-Type: application/json;charset=utf-8")
    @GET("discover/movie")
    suspend fun searchByDate(
        @Query ("api_key") api_key: String,
        @Query("page") page: Int,
        @Query("primary_release_date.gte") release_date_gte: String?,
        @Query("primary_release_date.lte") release_date_lte: String?
    ): MovieListResponse


}