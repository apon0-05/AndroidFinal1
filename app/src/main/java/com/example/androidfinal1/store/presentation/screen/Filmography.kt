package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.store.data.remote.ActorFilm
import com.example.androidfinal1.store.presentation.components.FilmographyTopBar
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState

//@Preview(showBackground = true)
@Composable
fun Filmography(actorId: Int?, navController: NavController
){
    Log.d("actorINFilmogra", "Clicked movie ID: ${actorId}")
    val viewModel: MoviesViewModel = viewModel()

    LaunchedEffect(actorId) {
        actorId?.let {
            viewModel.getActorDetailsFilmPo(it)
        }
    }

    val actorDetailsState = viewModel.actorDetailsState.collectAsState()
    val selectedProfession = remember { mutableStateOf<String?>(null) }
    val movies = viewModel.moviesactor.collectAsState()



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (val state = actorDetailsState.value) {
            is ScreenState.ActorInfoLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }

            is ScreenState.ActorFilmsSuccess -> {
                val actor = state.actor
                val filmsByProfession = state.filmsByProfession
                val professionTabs = filmsByProfession.keys.toList()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FilmographyTopBar(
                        actorName = actor.nameRu ?: actor.nameEn ?: "Unknown",
                        navController = navController
                    )

                    FilmList(
                        tabs = professionTabs,
                        selectedTab = selectedProfession.value,
                        onTabSelected = { selectedProfession.value = it }
                    )

                    //Spacer(modifier = Modifier.weight(1f))

                    val filmsToShow = filmsByProfession[selectedProfession.value] ?: emptyList()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filmsToShow) { film ->
                            FilmItem(
                                movie = film,
                                onClick = { navController.navigate("filmDetail/${film.filmId}") }

                            )
                        }
                    }

                }

            }

            is ScreenState.ActorInfoError -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ошибка: ${state.message}",
                        color = Color.Red,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun FilmList(tabs: List<String>, selectedTab: String?, onTabSelected: (String) -> Unit){
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tabs) { tab ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (selectedTab == tab) Color.Blue else Color(0xFFF2F2F2))
                    .clickable { onTabSelected(tab) }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = tab,
                    color = if (selectedTab == tab) Color.White else Color.Black,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium)
                )
            }
        }
    }

}

@Composable
fun FilmItem(movie: ActorFilm, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(width = 100.dp, height = 164.dp)
                .clip(RoundedCornerShape(10.dp))
                //.background(Color.Gray)
        ) {
            Log.d("FilmItem", "Poster URL: ${movie.posterUrl}")
            Image(

                painter = rememberImagePainter(data = movie.posterUrl),
                contentDescription = "Постер фильма",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .size(width = 20.dp, height = 12.dp)
                    .background(
                        Color.Blue,
                        RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.rating ?: "0.0",
                    fontSize = 7.sp,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = movie.nameRu ?: movie.nameEn ?: "Неизвестно",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.description ?: "Нет описания",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            )
        }
    }
}