package com.example.kiantest.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    val id: Int,
    val backdrop_path: String?,
    val original_title: String?,
    val release_date: String?,
    val overview: String?
) : Parcelable {
    val image_url get() = "https://image.tmdb.org/t/p/w500$backdrop_path"
}