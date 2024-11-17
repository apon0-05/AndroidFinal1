package com.example.androidfinal1.store.data.remote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// Retrofit API Interface for Kinopoisk
interface KinopoiskApiService {
    @Headers("X-API-KEY: cd3aeee9-bf7d-43fe-aea2-9ccdf67e3c50")
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: cd3aeee9-bf7d-43fe-aea2-9ccdf67e3c50")
    @GET("v2.2/films/collections")
    suspend fun getCollections(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: cd3aeee9-bf7d-43fe-aea2-9ccdf67e3c50")
    @GET("v2.2/films/collections?type:TOP_250_TV_SHOWS?page:1")
    suspend fun getZombie(
//        @Query("type") type: String,
//        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: cd3aeee9-bf7d-43fe-aea2-9ccdf67e3c50")
    @GET("v2.2/films/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int): MovieId

    @Headers("X-API-KEY: cd3aeee9-bf7d-43fe-aea2-9ccdf67e3c50")
    @GET("v1/staff")
    suspend fun getActor(@Query("filmId") movieId: Int): Actor



}


//interface ProductApi {
//    @Headers("X-API-KEY: c7df2853-6830-4671-8f3e-d99e3dde7b9d")
//    @GET("v2.2/films/{id}")
//    suspend fun getProducts(): List<Product>
//}