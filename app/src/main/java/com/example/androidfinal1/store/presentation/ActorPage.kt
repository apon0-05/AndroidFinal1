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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.data.remote.Movie
import com.example.androidfinal1.store.data.remote.MovieId
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState

@Composable
fun ActorPage(actorId: Int?, navController: NavController) {
    val viewModel: MoviesViewModel = viewModel()

    LaunchedEffect(actorId) {
        actorId?.let {
            viewModel.getActorDetails(it)
        }
    }

    val state = viewModel.actorDetailsState.collectAsState()
    val movies = viewModel.movies.collectAsState()

    when (val currentState = state.value) {

        is ScreenState.ActorInfoLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }

        is ScreenState.ActorInfoSuccess -> {
            val actor = currentState.actor
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.caretleft),
                    contentDescription = "Back",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(26.dp)
                        .size(24.dp)
                        .clickable { navController.popBackStack() }
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(data = actor.posterUrl),
                            contentDescription = "actor picture",
                            modifier = Modifier
                                .size(150.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = "${actor.nameRu}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text(text = "${actor.profession}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    // Display "Лучшее" (Best Movies) section
                    SecTitle("Лучшее", "Все", "", navController, actor.personId)
                    Log.d("FetchMovies", "Movies fetched: ${movies.value.size}")


                    if (movies.value.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            items(movies.value) { movie ->
                                MovieItem1(movie = movie, onClick = {
                                    navController.navigate("filmDetail/${movie.id}")
                                })
                            }
                            item {
                                AllShowButtonActor()
                            }
                        }
                    }

                    SecTitle("Фильмография", "К списку", "44 фильма", navController, actor.personId)
                }
            }
        }

        is ScreenState.ActorInfoError -> {
            Text(text = "Ошибка: ${currentState.message}", color = Color.Red)
        }

        else -> {
        }
    }
}

@Composable
fun MovieItem1(
    movie: MovieId,
    //onItemClick: (String) -> Unit,
    onClick: () -> Unit
    //isLastItem: Boolean,

) {
    Box(
        modifier = Modifier
        //     .clickable { onItemClick(movie.id.toString()) }
    ) {
        Column(
            modifier = Modifier.clickable {  onClick()  }
        ) {

            movie.posterUrl?.let { posterUrl ->
                Box(
                    modifier = Modifier
                        .size(width = 130.dp, height = 194.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onClick() /* action */ } //movie.id?.let { onClick(it)}

                ) {
                    Image(
                        painter = rememberImagePainter(data = posterUrl),
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(width = 24.dp, height = 16.dp)
                            .background(
                                Color.Blue,
                                RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = movie.rating?.toString() ?: "0.0",
                            fontSize = 9.sp,
                            color = Color.White,
                        )
                    }
                }

            }
            Text(
                text = movie.nameRu ?: "",
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(130.dp)
                    .padding(top = 5.dp)

            )
            Text(
                text = movie.genres.joinToString { it.name },
                fontSize = 15.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(130.dp)

            )


        }

    }
}


@Composable
fun SecTitle(title: String, text: String, filmCount: String, navController: NavController, actorId: Int?) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 26.dp, top = 26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            if (text == "К списку") {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navController.navigate("actorFilms/${actorId}")
                        Log.d("actorClick", "Clicked movie ID: ${actorId}")
                    }
                )
            } else {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Image(
                painter = painterResource(R.drawable.rigtharrow),
                contentDescription = "icons",
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = filmCount,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .padding(start = 26.dp, top = 8.dp)
        )
    }
}


@Composable
fun AllShowButtonActor(){
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
                    onClick = {},
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


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    ActorPage()
//}