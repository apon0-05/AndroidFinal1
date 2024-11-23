package com.example.androidfinal1.store.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState


@Composable
fun NewHomePage(viewModel: MoviesViewModel, navController: NavController) {
    // Собираем данные из ViewModel
    val premieresState by viewModel.premieresState.collectAsState()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()
    val zombieMoviesState by viewModel.zombieMoviesState.collectAsState()
//    val familMovies by viewModel.familyMovies.collectAsState(initial = emptyList())

    // Создаем список для хранения всех категорий
    val allMovies = listOf(premieresState, popularMoviesState, zombieMoviesState)
    val categoryNames = listOf("Премьеры", "Популярное", "Зомби")

    val isLoading = premieresState is ScreenState.Loading ||
            popularMoviesState is ScreenState.Loading ||
            zombieMoviesState is ScreenState.Loading

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
    } else {
        Text(
            text = "Skillcinema",
            modifier = Modifier.padding(top = 90.dp, start = 20.dp),
            fontSize = 23.sp
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 150.dp, start = 20.dp)
                    .weight(1f)
            ) {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
                    itemsIndexed(allMovies) { categoryIndex, state ->
                        when (state) {
                            is ScreenState.Loading -> {}
                            is ScreenState.Success -> {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth()
                                            .padding(end = 20.dp, bottom = 15.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = categoryNames.getOrNull(categoryIndex)
                                                ?: "Неизвестная категория",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Все",
                                            fontSize = 15.sp,
                                            color = Color.Blue,
                                            modifier = Modifier.clickable {
                                                navController.navigate("category/$categoryIndex")
                                            }
                                        )
                                    }
                                    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Log.d("Loh", state.movies.size.toString())
                                        items(state.movies.take(8)) { movie ->
                                           // Log.d("MovieDebug", "Movie ID before click: ${movie.id}")
                                            ItemView(
                                                movie = movie
                                                , onClick = {
                                                id ->
                                                Log.d("MovieClick", "Clicked movie ID: ${movie.id}")
                                                // Переход на экран с деталями фильма, передавая ID фильма
                                                  navController.navigate("filmDetail/${movie.id}")
                                            })
                                        }
                                        item {
                                            AllShowButton {
                                                navController.navigate("category/$categoryIndex")
                                            }
                                        }
                                    }
                                }
                            }

                            is ScreenState.Error -> {
                                Text(
                                    text = state.message,
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
            }
        }
    }

}


@Composable
fun AllShowButton(onShowAll: () -> Unit){
    Box(){
        Box(
            modifier = Modifier.size(width = 130.dp, height = 194.dp)
                .background(
                    color = Color(0x66B5B5C9),
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        topEnd = 4.dp,
                        bottomEnd = 4.dp,
                        bottomStart = 4.dp
                    )
                )
                .clip(RoundedCornerShape(10.dp)),
        ){
            Column(
                modifier = Modifier.padding(top = 55.dp, start = 25.dp)
            ){
                Button(
                    onClick = onShowAll,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 10.dp)
                        .size(40.dp)


                ){
                    Image(
                        painter = painterResource(id = R.drawable.showall),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(15.dp)
                            .clip(CircleShape)
                    )

                }
                Text(text = "Показать все", fontSize = 13.sp)
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewNewHomePage() {
//    val moviesViewModel = remember { MoviesViewModel() }
//    NewHomePage(moviesViewModel)
//}