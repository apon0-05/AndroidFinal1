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
    @SerializedName("professionText") val professionText: String?,

)

data class ImageResponse(
    @SerializedName("total") val total: Int?,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("items") val items: List<ImageItem>
)

data class ImageItem(
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("previewUrl") val previewUrl: String
)

data class SimilarFilmsResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("items") val items: List<SimilarFilmItem>
)

data class SimilarFilmItem(
    @SerializedName("filmId") val filmId: Int,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("posterUrl") val posterUrl: String?,

)

data class ActorDetailsResponse(
    @SerializedName("personId") val personId: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("posterUrl") val posterUrl: String?,
    @SerializedName("growth") val growth: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("death") val death: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("birthplace") val birthplace: String?,
    @SerializedName("deathplace") val deathplace: String?,
    @SerializedName("profession") val profession: String?,
    @SerializedName("facts") val facts: List<String>?,
    @SerializedName("films") val films: List<ActorFilm>?
)

data class ActorFilm(
    @SerializedName("filmId") val filmId: Int,
    @SerializedName("nameRu") val nameRu: String?,
    @SerializedName("nameEn") val nameEn: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("professionKey") val professionKey: String?,
    var posterUrl: String? = null
)
data class Genre(
    @SerializedName("genre") val name: String
)
data class Country(
    @SerializedName("country") val country: String
)



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
