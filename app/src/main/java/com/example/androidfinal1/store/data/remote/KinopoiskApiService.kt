package com.example.androidfinal1.store.data.remote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit API Interface for Kinopoisk
interface KinopoiskApiService {
    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/collections")
    suspend fun getCollections(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/collections?type:TOP_250_TV_SHOWS?page:1")
    suspend fun getZombie(
//        @Query("type") type: String,
//        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieId

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v1/staff")
    suspend fun getActor(@Query("filmId") movieId: Int): Actor

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v1/staff")
    suspend fun getStaffByFilmId(@Query("filmId") movieId: Int): List<Actor>


    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") filmId: Int,
        @Query("type") type: String = "STILL", // Можно изменить на другие типы изображений
        @Query("page") page: Int = 1
    ): ImageResponse

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") filmId: Int
    ): SimilarFilmsResponse

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("/api/v1/staff/{id}")
    suspend fun getActorDetails(
        @Path("id") actorId: Int
    ): ActorDetailsResponse

    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("v2.1/films/search-by-keyword")
    suspend fun searchFilms(
        @Query("keyword") keyword: String
    ): FilmResponse

//    @Headers("X-API-KEY: 414eb605-ae7f-48ad-a274-a99a2771130b")
//    @GET("v2.1/films/search-by-keyword")
//    suspend fun searchApon(
//        @Query("keyword") keyword: String,
//        @Query("page") page: Int = 1
//    ): Response<FilmsSearchResponse>
    @Headers("X-API-KEY: f7848c38-4310-4bae-b031-5ee11342aa65")
    @GET("/api/v2.2/films")
    suspend fun searchFilms(
        @Query("countries") countries: String?,
        @Query("genres") genres: String?,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("ratingFrom") ratingFrom: Float,
        @Query("ratingTo") ratingTo: Float,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): MovieSearchResponse







}

