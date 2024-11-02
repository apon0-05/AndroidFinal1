package com.example.androidfinal1.store.data.remote

import com.google.gson.annotations.SerializedName

// Data classes for API response
data class Movie(
    @SerializedName("nameRu") val title: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("posterUrlPreview") val posterUrl: String?,
    @SerializedName("ratingKinopoisk") val rating: Double?
)

data class Genre(
    @SerializedName("genre") val name: String
)

data class MoviesResponse(val movies: List<Movie>)

data class PremieresResponse(
    @SerializedName("items") val movies: List<Movie>
)
data class ZombieResponse(
    @SerializedName("items") val movies: List<Movie>
)
//data class Product(
//    val nameRu: String,
//    val posterUrlPreview: String,
//    val genres: String,
//)
