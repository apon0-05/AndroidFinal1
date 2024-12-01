package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.components.FilmCard
import com.example.androidfinal1.store.presentation.components.SearchBar
import com.example.androidfinal1.store.presentation.components.ShowContent
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState

@Composable
fun SearchPage(viewModel: MoviesViewModel = hiltViewModel(), navController: NavController) {
    val query = remember { mutableStateOf("") }
    val searchFilmsState by viewModel.searchFilmsState.collectAsState()

    LazyColumn {
        item {
            SearchBar(
                query = query.value,
                onQueryChange = { newQuery -> query.value = newQuery },
                onSearchClick = {
                    if (query.value.isNotEmpty()) {
                        viewModel.searchFilms(query.value)
                    }
                },
                onPreferencesClick = {
                    navController.navigate("search_preferences")
                }

            )
        }

        when (searchFilmsState) {
            is ScreenState.Loading -> {
                item { Text("Загрузка...") }
            }
            is ScreenState.Error -> {
                item { Text("Ошибка: ${(searchFilmsState as ScreenState.Error).message}") }
            }
            is ScreenState.SuccessMovieId -> {
                val movies = (searchFilmsState as ScreenState.SuccessMovieId).movies
                items(movies) { movie ->
                    FilmCard(
                        movie = movie,
                        onClick = {
                            // Переход на страницу фильма
                            navController.navigate("filmDetail/${movie.id}")
                        }
                    )
                }
            }
            else -> {
                // Можно добавить другие состояния или пустой блок
            }
        }

    }
}




//
//@Composable
//@Preview(showBackground = true)
//fun SearchPagePreview() {
//    SearchPage()
//}