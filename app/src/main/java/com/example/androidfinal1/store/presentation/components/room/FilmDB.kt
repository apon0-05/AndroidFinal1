package com.example.androidfinal1.store.presentation.components.room
import androidx.room.ColumnInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidfinal1.store.data.remote.Genre
import com.example.androidfinal1.store.data.remote.MovieId

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val nameRu: String?,
    val posterUrl: String?,
    val rating: Double,
    val year: Int?,
   // val genres: String, // Сохраняем как JSON-строку
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false
)
