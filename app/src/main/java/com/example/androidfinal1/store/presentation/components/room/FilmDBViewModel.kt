package com.example.androidfinal1.store.presentation.components.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidfinal1.store.data.remote.MovieId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilmDBViewModel(
    private val dao: MovieDao,
    private val viewedMovieDao: ViewedMovieDao
) : ViewModel() {

    val viewedMoviesuwu: Flow<List<ViewedMovie>> = viewedMovieDao.getViewedMovies()

   // val viewedMoviesuwu = viewedMovieDao.getViewedMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun addViewedMovie(movie: MovieId) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    viewedMovieDao.addViewedMovie(movie.toEntity())
                }catch (e: Exception){


                }
            }

        }
    }
    fun clearHistory() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    viewedMovieDao.clearHistory()
                }catch (e:Exception){

                }
            }

        }
    }



    val favoriteMovies = dao.getFavoriteMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val watchLaterMovies = dao.getWatchLaterMovies().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addToFavorites(movie: MovieId) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dao.upsertMovie(movie.toEntity(isFavorite = true))
                    Log.d("FilmDBViewModel", "Movie added to favorites: ${movie.id}")
                } catch (e: Exception) {
                    Log.e("FilmDBViewModel", "Error upserting movie: ${e.message}")

                }
            }

        }
    }
    val favoriteMoviesCount: Flow<Int> = dao.getFavoriteMoviesCount().asLiveData().asFlow()

    val watchLaterMoviesCount : Flow<Int> = dao.getWatchLaterMoviesCount().asLiveData().asFlow()

    fun addToWatchLater(movie: MovieId) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dao.upsertMovie(movie.toEntity(isWatchLater = true))
                    Log.d("FilmDBViewModel", "Movie added to WatchLater: ${movie.id}")
                } catch (e: Exception) {
                    Log.e("MoviesViewModel", "Error upserting movie: ${e.message}")

                }
            }

        }
    }



    private fun MovieId.toEntity(isFavorite: Boolean = false, isWatchLater: Boolean = false): MovieEntity {
        return MovieEntity(
            id = this.id ?: 0,
            nameRu = this.nameRu,
            posterUrl = this.posterUrl,
            rating = this.rating ?: 0.0,
            year = this.year,
           // genres = this.genres.joinToString { it.name },
            isFavorite = isFavorite,
            isWatchLater = isWatchLater
        )
    }

    private fun MovieId.toEntity(): ViewedMovie {
        return ViewedMovie(
            id = this.id ?: 0,
            nameRu = this.nameRu,
            posterUrl = this.posterUrl,
            rating = this.rating ?: 0.0,
            year = this.year,
            // genres = this.genres.joinToString { it.name },

        )
    }


    fun deleteAllFilms() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dao.deleteAllMovies()
                } catch (e: Exception) {
                    Log.e("FilmDBViewModel", "Error deleting films: ${e.message}")
                }
            }
        }
    }

    fun deleteAllFavoriteMovies() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dao.deleteAllFavoriteMovies()
                } catch (e: Exception) {
                    Log.e("FilmDBViewModel", "Error deleting favorite films: ${e.message}")
                }
            }
        }
    }

    fun deleteAllWatchLaterMovies(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                try{
                    dao.deleteAllWatchLaterMovies()
                } catch (e: Exception){
                    Log.e("FilmDBViewModel", "Error deleting watch later films: ${e.message}")
                }
            }

        }
    }


}

