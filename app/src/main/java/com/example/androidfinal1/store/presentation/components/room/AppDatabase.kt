package com.example.androidfinal1.store.presentation.components.room


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class, ViewedMovie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun viewedMovieDao(): ViewedMovieDao
}