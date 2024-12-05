package com.example.androidfinal1.store.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.components.room.FilmDBViewModel
import com.example.androidfinal1.store.presentation.components.room.MovieEntity


@Composable
fun WanttiwatchPage(
    viewModel: FilmDBViewModel,
    navController: NavController
){


    val wanttoWatchMovies by viewModel.watchLaterMovies.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp, vertical = 26.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(
                painter = painterResource(id = R.drawable.caretleft),
                contentDescription = "Back",
                modifier = Modifier
                    //.padding(start = 26.dp, top = 26.dp)
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
                colorFilter = ColorFilter.tint(Color.Black)
            )

            Text(
                text = "Хочу посмотреть",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.deleteicon),
                contentDescription = "delete",
                modifier = Modifier
                    //.padding(end = 26.dp, top = 26.dp)
                    .size(24.dp)
                    .clickable { viewModel.deleteAllWatchLaterMovies()},
                colorFilter = ColorFilter.tint(Color.Black)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 50.dp, start = 20.dp)
                .weight(1f)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 40.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(wanttoWatchMovies) { movie ->
                    ItemViewUwU(movie = movie, onClick = {
                         navController.navigate("filmDetail/${movie.id}")
                    })
                }
            }
        }
    }
}


