package com.example.androidfinal1.store.presentation.components.room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMovie(movie: MovieEntity)

//    @Delete
//    suspend fun deleteMovie(movie: MovieEntity)


    @Query("DELETE FROM movies")
    fun deleteAllMovies()


    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE isWatchLater = 1")
    fun getWatchLaterMovies(): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(*) FROM movies WHERE isFavorite = 1")
    fun getFavoriteMoviesCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM movies WHERE isWatchLater = 1")
    fun getWatchLaterMoviesCount(): Flow<Int>

    @Query("DELETE FROM movies WHERE isFavorite = 1")
    fun deleteAllFavoriteMovies()

    @Query("DELETE FROM movies WHERE  isWatchLater = 1")
    fun deleteAllWatchLaterMovies()
}

