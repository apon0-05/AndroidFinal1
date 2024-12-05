package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.data.remote.Actor
import com.example.androidfinal1.store.data.remote.Movie
import com.example.androidfinal1.store.data.remote.MovieId
import com.example.androidfinal1.store.presentation.components.ItemView
import com.example.androidfinal1.store.presentation.components.room.FilmDBViewModel
//import com.example.androidfinal1.store.presentation.components.room.FilmViewModel
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ScreenForFinal1Theme {
//                FilmPage()
//            }
//        }
//    }
//}

@Composable
fun FilmPage(movieId: Int?, navController: NavController, movieViewModel: FilmDBViewModel) { //filmViewModel: FilmViewModel
    val viewModel: MoviesViewModel = viewModel()


    LaunchedEffect(movieId) {
        movieId?.let {
            viewModel.getFilmDetails(it)
            viewModel.getStaffCount(it)
            viewModel.getSimilarFilms(it)
            viewModel.getFilmImages(it)
        }
    }
    val state = viewModel.filmDetailsState.collectAsState()
    val staffState = viewModel.staffCount.collectAsState()
    val similarFilmsCount = viewModel.getSimilarFilmsCount()
    val imagesCount = viewModel.getFilmImagesCount()


    when (val currentState = state.value ){
        is ScreenState.FilmLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenState.FilmSuccess -> {
        //val movie = currentState.movie

            when (val staff = staffState.value) {
                is ScreenState.ActorLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
                is ScreenState.ActorSuccess -> {
                    val movie = currentState.movie
                    val actors = staff.actor.filter { it.professionText == "Актеры" }
                    val directors = staff.actor.filter { it.professionText == "Режиссеры" }

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .weight(1f)
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(2.dp)

                            ) {


//            Column(
//                modifier = Modifier
//                    .fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
                                item {
                                    MovieInfo(movie, navController, movieViewModel) //filmViewModel
                                }
                                item {
                                    Description(movie)
                                }
                                item {
                                    SectionTitle(title = "В фильме снимались", count = actors.size, onClick = {})
                                }
                                item {
                                    CastList(actors, navController)
                                }
                                item {
                                    SectionTitle(title = "Над фильмом работали", count = directors.size, onClick = {})
                                }
                                item {
                                    CrewList(directors)
                                }
                                item {
                                    SectionTitle(title = "Галерея", count = imagesCount, onClick = {
                                        navController.navigate("filmmDetail/${movie.id}")
                                    })
                                    Log.d("Galerysize", imagesCount.toString())
                                }
                                item {
                                    movie.id?.let { Gallery(it) }

                                }
                                item {
                                    SectionTitle(title = "Похожие фильмы", count = similarFilmsCount, onClick = {})
                                }
                                item {
                                    movie.id?.let { SimilarMovies(it) }
                                }

                            }
                        }

                    }

                }
                is ScreenState.ActorError -> {
                    Text(
                        text = "Ошибка загрузки актеров: ${staff.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                }
            }
        }
        is ScreenState.FilmError -> {
            Text(text ="ura" ) //currentState.message, color = Color.Gray
        }
        ScreenState.Initial -> Unit
        else -> {
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieInfo(movie: MovieId, navController: NavController, movieViewModel: FilmDBViewModel) { //filmViewModel: FilmViewModel
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    movieViewModel.addViewedMovie(movie)



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = movie.posterUrl),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Image(
            painter = painterResource(id = R.drawable.caretleft),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(26.dp)
                .size(24.dp)
                .clickable { navController.popBackStack() },
            colorFilter = ColorFilter.tint(Color.White)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${movie.nameRu}",
                //modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = 35.sp

            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${movie.rating ?: ""}   ${movie.nameRu}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = "${movie.year ?: ""}, ${movie.genres.joinToString(", ") { it.name }}",
                fontSize = 14.sp,
                color = Color.White,
            )
            Text(
                text = "${movie.countries.joinToString(", ") { it.country }}, ${movie.filmLength ?: ""} мин, ${movie.ratingAgeLimits ?: ""}",
                fontSize = 14.sp,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                IconsHolder(
                    img = R.drawable.icons,
                    movie = movie,
                    onClick = { movie.id?.let {
                        Log.d("MovieClickFavourite", "Clicked movie ID: ${movie.id}")}
                        movieViewModel.addToFavorites(movie)
                        Log.d("MovieClickFavourite", "this id movie added to favFilms: ${movie.id}")
                    }

                )

                IconsHolder(
                    img = R.drawable.icons2,
                    movie = movie,
                    onClick = { movie.id?.let { movieViewModel.addToWatchLater(movie) }
                        Log.d("MovieClickWatchLater", "this id movie added to watchlater: ${movie.id}")
                    }
                )
                IconsHolder(img = R.drawable.icons3,  movie = movie,  onClick = {
                    movieViewModel.deleteAllFilms()
                })
                IconsHolder(img = R.drawable.icons4, movie = movie,  onClick = {})
                IconsHolder(img = R.drawable.icons5,  movie = movie,  onClick = {showBottomSheet = true }) //viewModel = filmViewModel,
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    Text(
                        "Swipe up to open sheet. Swipe down to dismiss.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }


        }
    }
}

@Composable
fun IconsHolder(img: Int,  movie: MovieId,  onClick: (Int) -> Unit) { //viewModel: FilmViewModel
    Box(
        modifier = Modifier
            .size(25.dp)
            .clickable { movie.id?.let { onClick(it) } }
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = "icons",
            modifier = Modifier.fillMaxSize(),
            colorFilter = ColorFilter.tint(Color.White)
        )

    }
}

@Composable
fun Description(movie: MovieId) {
    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(26.dp)
    ){
        Text(
            text = "${movie.description}",
            fontSize = 16.sp,
            fontWeight = Bold

        )
        Text(
            text = "${movie.shortDescription ?: ""}",
            fontSize = 16.sp
        )
    }
}


@Composable
fun SectionTitle(title: String, count: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = count.toString(),
            fontSize = 14.sp,
            color = Color.Blue,
            modifier = Modifier.clickable { onClick() }
        )
        Image(
            painter = painterResource(R.drawable.rigtharrow),
            contentDescription = "icons",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun CastList(actors: List<Actor>, navController: NavController) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(4),
        modifier = Modifier
            .padding(start = 26.dp)
            .fillMaxWidth()
            .height(300.dp),
       // horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(actors) { actor ->
            InfoCards(actor = actor, onClick = {
                id -> navController.navigate("actorDetail/${actor.staffId}")
            })
        }
    }
}
@Composable
fun InfoCards(
    actor: Actor,
    onClick: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable { actor.staffId?.let { onClick(it) }  }
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(actor.posterUrl),
                contentDescription = "Cast Member",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(text = actor.nameRu ?: "Неизвестно", fontSize = 12.sp)
                Text(text = actor.professionText ?: "Неизвестная роль",  fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CrewList(actors: List<Actor>) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        modifier = Modifier
            .padding(start = 26.dp)
            .fillMaxWidth()
            .height(175.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),

    ) {
        items(actors) { actor ->
            InfoCards(actor, onClick = {})
        }
    }
}



@Composable
fun Gallery(movieId: Int) {
    val viewModel: MoviesViewModel = viewModel()

    LaunchedEffect(movieId) {
        viewModel.getFilmImages(movieId)
    }

    val state = viewModel.imagesState.collectAsState()
    when(val currentState = state.value ){
        is ScreenState.ImageLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenState.ImageSuccess -> {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1), // Одна строка
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                items(currentState.images) { image ->
                    Image(
                        painter = rememberImagePainter(image.previewUrl),
                        contentDescription = "Gallery Image",
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f)
                            .padding(8.dp)
                    )
                }
            }
        }
        is ScreenState.ImageError -> {
            Text(text = currentState.message, color = Color.Gray)
        }
        else -> Unit
    }
}



@Composable
fun SimilarMovies(filmId: Int) {
    val viewModel: MoviesViewModel = viewModel()

    LaunchedEffect(filmId) {
        viewModel.getSimilarFilms(filmId)
    }

    val state = viewModel.similarFilmsState.collectAsState()

    when (val currentState = state.value) {
        is ScreenState.SimilarFilmsLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenState.SimilarFilmsSuccess -> {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1), // Одна строка
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                items(currentState.films) { film ->
                    ItemView(
                        movie = Movie(
                            id = film.filmId,
                            title = film.nameRu,
                            posterUrl = film.posterUrl,
                            rating = null,
                            genres = emptyList()
                        ),
                        onClick = { movieId -> /* Действие при клике на фильм */ }
                    )
                }
            }
        }
        is ScreenState.SimilarFilmsError -> {
            Text(text = currentState.message, color = Color.Gray)
        }
        else -> Unit
    }
}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FilmPage()
//}