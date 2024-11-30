package com.example.androidfinal1.store.data.remote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit API Interface for Kinopoisk
interface KinopoiskApiService {
    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/collections")
    suspend fun getCollections(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/collections?type:TOP_250_TV_SHOWS?page:1")
    suspend fun getZombie(
//        @Query("type") type: String,
//        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieId

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v1/staff")
    suspend fun getActor(@Query("filmId") movieId: Int): Actor

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v1/staff")
    suspend fun getStaffByFilmId(@Query("filmId") movieId: Int): List<Actor>


    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/{id}/images")
    suspend fun getFilmImages(
        @Path("id") filmId: Int,
        @Query("type") type: String = "STILL", // Можно изменить на другие типы изображений
        @Query("page") page: Int = 1
    ): ImageResponse

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.2/films/{id}/similars")
    suspend fun getSimilarFilms(
        @Path("id") filmId: Int
    ): SimilarFilmsResponse

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("/api/v1/staff/{id}")
    suspend fun getActorDetails(
        @Path("id") actorId: Int
    ): ActorDetailsResponse

    @Headers("X-API-KEY: f8f3d444-6758-4b3b-94a1-44a87e2834dc")
    @GET("v2.1/films/search-by-keyword")
    suspend fun searchFilms(
        @Query("keyword") keyword: String
    ): FilmResponse




}


//interface ProductApi {
//    @Headers("X-API-KEY: c7df2853-6830-4671-8f3e-d99e3dde7b9d")
//    @GET("v2.2/films/{id}")
//    suspend fun getProducts(): List<Product>
//}