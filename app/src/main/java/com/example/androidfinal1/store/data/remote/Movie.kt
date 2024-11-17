package com.example.androidfinal1.store.data.remote

import com.google.gson.annotations.SerializedName

// Data classes for API response
data class Movie(
    @SerializedName("nameRu") val title: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("posterUrlPreview") val posterUrl: String?,
    @SerializedName("ratingKinopoisk") val rating: Double?,
    @SerializedName("kinopoiskId") val id: Int?,

)

data class MovieId(
    @SerializedName("nameEn") val title: String?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("posterUrlPreview") val posterUrl: String?,
    @SerializedName("ratingKinopoisk") val rating: Double?,
    @SerializedName("kinopoiskId") val id: Int?,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("ratingAgeLimits") val ratingAgeLimits: String?,
    @SerializedName("countries") val countries: List<Country>,
    @SerializedName("filmLength") val filmLength: Int?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("description") val description: String?,


)

data class Actor(
    @SerializedName("staffId") val staffId: Int?,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("professionKey") val professionKey: String?,
    @SerializedName("posterUrl") val posterUrl: String?,

)



data class Genre(
    @SerializedName("genre") val name: String
)
data class Country(
    @SerializedName("country") val country: String
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
