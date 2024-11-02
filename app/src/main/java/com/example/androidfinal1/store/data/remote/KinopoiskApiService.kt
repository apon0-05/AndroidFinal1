package com.example.androidfinal1.store.data.remote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

// Retrofit API Interface for Kinopoisk
interface KinopoiskApiService {
    @Headers("X-API-KEY: a559e5fe-a8bc-47af-9ca3-ea835093c6e5")
    @GET("v2.2/films/premieres")
    suspend fun getPremieres(
        @Query("year") year: Int,
        @Query("month") month: String
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: a559e5fe-a8bc-47af-9ca3-ea835093c6e5")
    @GET("v2.2/films/collections")
    suspend fun getCollections(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<PremieresResponse>

    @Headers("X-API-KEY: a559e5fe-a8bc-47af-9ca3-ea835093c6e5")
    @GET("v2.2/films/collections?type:TOP_250_TV_SHOWS?page:1")
    suspend fun getZombie(
//        @Query("type") type: String,
//        @Query("page") page: Int
    ): Response<PremieresResponse>
}


//interface ProductApi {
//    @Headers("X-API-KEY: c7df2853-6830-4671-8f3e-d99e3dde7b9d")
//    @GET("v2.2/films/{id}")
//    suspend fun getProducts(): List<Product>
//}