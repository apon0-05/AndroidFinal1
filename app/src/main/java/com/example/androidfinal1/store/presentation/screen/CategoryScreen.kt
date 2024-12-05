package com.example.androidfinal1.store.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.components.ItemView
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState


@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryScreen(categoryIndex: Int, navController: NavController) {
    val viewModel: MoviesViewModel = viewModel()
    val state = when (categoryIndex) {
        0 -> viewModel.premieresState.collectAsState().value
        1 -> viewModel.popularMoviesState.collectAsState().value
        2 -> viewModel.zombieMoviesState.collectAsState().value
        // 3 -> viewModel.familyMoviesState.collectAsState().value
        else -> ScreenState.Error("Invalid category")
    }


    val categoryNames = listOf("Премьеры", "Популярные", "Зомби", "Family")
    val categoryName = categoryNames.getOrNull(categoryIndex) ?: "Неизвестная категория"

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(15.dp)
                    .clickable { navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(130.dp))
            Text(text = categoryName, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(70.dp))

        when (state) {
            is ScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
            is ScreenState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 60.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items((state as ScreenState.Success).movies.take(40)) { movie ->
                        Log.d("MovieDebug", "Movie ID before click: ${movie.id}")
                        ItemView(movie)
                        { id ->
                            Log.d("MovieClick", "Clicked movie ID: ${movie.id}")

                            navController.navigate("filmDetail/${movie.id}")
                        }
                    }
                }
            }
            is ScreenState.Error -> {
                Text(
                    text = (state as ScreenState.Error).message,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            ScreenState.Initial -> Unit

            else -> {}
        }
    }
}