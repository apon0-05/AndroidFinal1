package com.example.androidfinal1.store.domain.repository

import com.example.androidfinal1.store.data.remote.KinopoiskApi
import com.example.androidfinal1.store.data.remote.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieRepository {

    fun fetchPremieres(year: Int, month: String, onResult: (List<Movie>?, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = KinopoiskApi.retrofitService.getPremieres(year, month)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        onResult(response.body()?.movies, null) // Возвращаем список фильмов
                    } else {
                        onResult(null, "Ошибка: ${response.message()}") // Ошибка от сервера
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(null, "Ошибка сети: ${e.message}") // Ошибка сети
                }
            }
        }
    }

    fun fetchCollections(type: String, page: Int, onResult: (List<Movie>?, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = KinopoiskApi.retrofitService.getCollections(type, page)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        onResult(response.body()?.movies, null) // Возвращаем список фильмов
                    } else {
                        onResult(null, "Ошибка: ${response.message()}") // Ошибка от сервера
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(null, "Ошибка сети: ${e.message}") // Ошибка сети
                }
            }
        }
    }

    fun fetchZombies(type: String, page: Int, onResult: (List<Movie>?, String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = KinopoiskApi.retrofitService.getZombie()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        onResult(response.body()?.movies, null) // Возвращаем список фильмов
                    } else {
                        onResult(null, "Ошибка: ${response.message()}") // Ошибка от сервера
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onResult(null, "Ошибка сети: ${e.message}") // Ошибка сети
                }
            }
        }
    }
}





//interface ProductsRepository {
//    suspend fun getProducts(): Either<NetworkError,List<Product>>
//}