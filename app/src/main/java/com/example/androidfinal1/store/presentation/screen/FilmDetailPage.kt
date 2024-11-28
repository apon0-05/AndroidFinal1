//package com.example.androidfinal1.store.presentation
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import coil.compose.rememberImagePainter
//import com.example.androidfinal1.R
//import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
//import com.example.androidfinal1.store.presentation.viewmodel.ScreenState
//import com.google.firebase.inappmessaging.model.ImageData
//
//@Composable
//fun FilmDetailPage(movieId: Int?, navController: NavController) {
//    val viewModel: MoviesViewModel = viewModel()
//
//    LaunchedEffect(movieId) {
//        movieId?.let {
//            viewModel.getFilmDetails(it)
//        }
//    }
//    val state = viewModel.filmDetailsState.collectAsState()
//
//    //val loading = state is ScreenState.FilmLoading
//
////    Column(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(16.dp)
////    ) {
////        when (val currentState = state.value) {
////            is ScreenState.FilmLoading -> {
////                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
////            }
////
////            is ScreenState.FilmSuccess -> {
////                Column(
////                    modifier = Modifier.fillMaxSize()
////                ) {
////                    val movie = currentState.movie
////                    // Отображение информации о фильме
////                    Text(text = "Title: ${movie.title}")
////                    // Text(text = "Description: ${movie.description}")
////                    Text(text = "Rating: ${movie.year}")
////                    // Добавьте другие данные фильма по мере необходимости
////                    Icon(
////                        painter = painterResource(id = R.drawable.back),
////                        contentDescription = "Back",
////                        modifier = Modifier
////                            .size(15.dp)
////                            .clickable { navController.popBackStack() }
////                    )
////
////                }
////
////            }
////
////            is ScreenState.FilmError -> {
////                Text(
////                    text = currentState.message,
////                    color = Color.Gray
////                ) //currentState.message, color = Color.Gray
////            }
////
////            ScreenState.Initial -> Unit
////            else -> {
////                // Другие состояния, если они будут добавлены в будущем
////            }
////        }
////    }
//
//
//
//
//
//
//    when (val currentState = state.value) {
//
//        is ScreenState.FilmLoading -> {
//            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
//        }
//        is ScreenState.FilmSuccess -> {
//            val movie = currentState.movie
//
//            val iconList = listOf(
//                ImageData(R.drawable.icon),
//                ImageData(R.drawable.icon__1_),
//                ImageData(R.drawable.icon__2_),
//                ImageData(R.drawable.icon__3_),
//                ImageData(R.drawable.icon__4_)
//            )
//
//
//            Column(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Box(
//                    modifier = Modifier
//                        .padding(bottom = 20.dp)
//                        .size(width = 420.dp, height = 400.dp)
//                        .clickable { /* action */ }
//                        .fillMaxSize()
//
//
//                ) {
//                    Image(
//                        painter = rememberImagePainter(data = movie.posterUrl),
//                        contentDescription = "Movie Poster",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.matchParentSize()
//                    )
//
//                    Image(
//                        painter = painterResource(id = R.drawable.iconfilmpage),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .align(Alignment.TopStart)
//                            .padding(25.dp)
//                            .size(15.dp)
//                            .clickable { navController.popBackStack() },
//                        colorFilter = ColorFilter.tint(Color.White)
//                    )
//                    Box(
//                        modifier = Modifier.align(Alignment.BottomCenter).padding(30.dp)
//                    ) {
//                        Column {
//                            Column(
//                                modifier = Modifier.fillMaxWidth()
//                                    .padding(vertical = 10.dp)
//
//                            ) {
//                                Text(
//                                    text = "${movie.nameRu}",
//                                    modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
//                                    color = Color.White,
//                                    fontSize = 35.sp
//
//                                )
//                                Text(
//                                    text = "${movie.rating ?: ""}   ${movie.nameRu}", //"rating + name in rus"
//                                    modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
//                                    color = Color.White
//                                )
//                                Text(
//                                    text = "${movie.year ?: ""}, ${movie.genres.joinToString(", ") { it.name }}", //"year + genre"
//                                    modifier = Modifier.padding(5.dp) .align(Alignment.CenterHorizontally),
//                                    color = Color.White
//                                )
//                                Text(
//                                    text = "${movie.countries.joinToString(", ") { it.country }}, ${movie.filmLength ?: ""} мин, ${movie.ratingAgeLimits ?: ""}", //"country + time + age"
//                                    modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
//                                    color = Color.White
//                                )
//                            }
//
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(horizontal = 50.dp),
//                                horizontalArrangement = Arrangement.SpaceEvenly
//                            ) {
//                                iconList.forEach { iconData ->
//                                    Image(
//                                        painter = painterResource(iconData.int),
//                                        contentDescription = null,
//                                        modifier = Modifier.size(15.dp),
//                                        colorFilter = ColorFilter.tint(Color.White)
//
//                                    )
//                                }
//                            }
//
//                        }
//                    }
//
//
////                Image(
////                    painter = rememberImagePainter(data = posterUrl),
////                    contentDescription = "Movie Poster",
////                    contentScale = ContentScale.Crop,
////                    modifier = Modifier.matchParentSize()
////                )
//                }
//
//                Box(
//                    modifier = Modifier.weight(1f).padding(start = 25.dp)
//
//                ) {
//                    LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)){
//                        item {
//                            Text(
//                                text = "${movie.description}",
//                                fontSize = 17.sp,
//                                color = Color.Black,
//                                fontWeight = FontWeight.Bold,
//                                maxLines = 5, // Limit the text to two lines
//                                overflow = TextOverflow.Ellipsis, // Add "..." if the text exceeds the max lines
//                                modifier = Modifier.padding(end = 20.dp, bottom = 20.dp)
//                            )
//                            Text(
//                                text = "${movie.shortDescription ?: ""}",
//                                fontSize = 17.sp,
//                                color = Color.Black,
//                                maxLines = 5, // Limit the text to two lines
//                                overflow = TextOverflow.Ellipsis, // Add "..." if the text exceeds the max lines
//                                modifier = Modifier.padding(end = 20.dp)
//                            )
//
//                        }
//
//                    }
//
//
//                }
//
//
//
//            }
//
//
//        }
//        is ScreenState.FilmError -> {
//            Text(text ="ura" ) //currentState.message, color = Color.Gray
//        }
//        ScreenState.Initial -> Unit
//        else -> {
//            // Другие состояния, если они будут добавлены в будущем
//        }
//    }
//
//
//
//
//}
//
