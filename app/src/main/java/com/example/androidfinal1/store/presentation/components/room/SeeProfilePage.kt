package com.example.androidfinal1.store.presentation.components.room

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
//
//@Composable
//fun SeeProfilePage(viewModel: FilmViewModel) {
//
//    val favoritesCount by viewModel.getFavoritesCount().collectAsState(initial = 0)
//    val watchlistCount by viewModel.getWatchlistCount().collectAsState(initial = 0)
//
//
//    val favorites by viewModel.getFilms("Любимые").collectAsState(initial = emptyList())
//    val watchlist by viewModel.getFilms("Хочу посмотреть").collectAsState(initial = emptyList())
//
//    Column {
//
//        Text(text = "Любимые: ${favorites.size}")
//        Text(text = "Хочу посмотреть: ${watchlist.size}")
//        LazyColumn {
//            items(favorites){ film ->
//                Text(text = film.title ?: "Название отсутствует")
//
//            }
//            items(watchlist) { film ->
//                Text(text = film.title ?: "Название отсутствует")
//            }
//        }
//    }
//}