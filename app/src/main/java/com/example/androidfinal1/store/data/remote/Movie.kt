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
    var posterUrl: String? = null,
    var year: Int? = null, // Новый параметр
    var genres: String? = null
)




data class PremieresResponse(
    @SerializedName("items") val movies: List<Movie>
)
data class ZombieResponse(
    @SerializedName("items") val movies: List<Movie>
)

//data class AponSearchResponse(
//    @SerializedName("films") val films: List<AponFilm>,
//    @SerializedName("searchFilmsCountResult") val searchFilmsCountResult: Int
//)
//data class AponFilm(
//    val filmId: Int,
//    val nameRu: String,
//    val nameEn: String,
//    val type: String,
//    val year: String,
//    val description: String,
//    val filmLength: String,
//    val countries: List<Country>,
//    val genres: List<Genre>,
//    val rating: String,
//    val ratingVoteCount: Int,
//    val posterUrl: String,
//    val posterUrlPreview: String
//)

data class Genre(
    @SerializedName("genre") val name: String
)
data class Country(
    @SerializedName("country") val country: String
)


data class FilmResponse(
    @SerializedName("searchFilmsCountResult") val searchFilmsCountResult: Int,
    @SerializedName("films") val films: List<Film>
)

data class MovieSearchResponse(
    @SerializedName("total") val total: Int, // Общее количество фильмов по запросу
    @SerializedName("totalPages") val totalPages: Int, // Общее количество страниц (для пагинации)
    @SerializedName("items") val items: List<Movie> // Список фильмов
)

data class MovieSearch(
    @SerializedName("kinopoiskId") val kinopoiskId: Int, // ID фильма на КиноПоиск
    @SerializedName("imdbId") val imdbId: String, // IMDb ID
    @SerializedName("nameRu") val nameRu: String, // Название на русском
    @SerializedName("nameEn") val nameEn: String, // Название на английском
    @SerializedName("nameOriginal") val nameOriginal: String, // Оригинальное название
    val countries: List<Country>, // Список стран
    val genres: List<Genre>, // Список жанров
    val ratingKinopoisk: Double, // Рейтинг на КиноПоиск
    val ratingImdb: Double, // Рейтинг на IMDb
    val year: Int, // Год выпуска
    val type: String, // Тип (фильм, сериал, и т.д.)
    val posterUrl: String, // URL изображения постера
    val posterUrlPreview: String // URL изображения постера для превью
)

fun MovieSearch.toMovieId(): MovieId {
    return MovieId(
        title = this.nameEn,
        genres = this.genres, // Преобразование жанров
        posterUrl = this.posterUrlPreview,
        rating = this.ratingKinopoisk,
        id = this.kinopoiskId,
        nameRu = this.nameRu,
        year = this.year,
        ratingAgeLimits = null, // Здесь можно добавить логику, если нужно использовать рейтинг возрастных ограничений
        countries = this.countries, // Преобразование стран
        filmLength = null, // Здесь нужно добавить логику, если в Movie есть длина фильма
        shortDescription = "", // Здесь можно добавить логику для краткого описания
        description = "" // Для полного описания можно добавить логику
    )
}

data class Film(
    @SerializedName("filmId") val filmId: Int,
    @SerializedName("nameRu") val nameRu: String,
    @SerializedName("nameEn") val nameEn: String,
    @SerializedName("year") val year: String,
    @SerializedName("description") val description: String,
    //@SerializedName("ratingKinopoisk") val rating: String,
    @SerializedName("posterUrlPreview") val posterUrl: String,
    @SerializedName("genres") val genres: List<Genre>

    )

// Преобразуем Film в MovieId
fun Film.toMovieId(): MovieId {
    return MovieId(
        title = this.nameRu,
        genres = this.genres,
        posterUrl = this.posterUrl,
        rating = 0.0, //this.rating?.takeIf { it.isNotEmpty() }?.toDoubleOrNull() ?: 0.0,
        id = this.filmId,
        nameRu = this.nameRu,
        year = this.year.toIntOrNull() ?: 0,
        ratingAgeLimits = null, // В Film нет информации о возрастных ограничениях, если оно нужно — уточните
        countries = emptyList(), // Пустой список стран, если это нужно — добавьте реальные данные
        filmLength = null, // В Film нет длины фильма, если это нужно — добавьте реальное поле
        shortDescription = this.description, // Описание
        description = this.description // Полное описание
    )
}



//data class Product(
//    val nameRu: String,
//    val posterUrlPreview: String,
//    val genres: String,
//)
