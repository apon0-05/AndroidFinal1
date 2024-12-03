package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.components.ItemView
import com.example.androidfinal1.store.presentation.components.room.FilmDBViewModel
//import com.example.androidfinal1.store.presentation.components.room.FilmViewModel
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState

@Composable
fun ProfilePage(viewModel: MoviesViewModel, navController: NavController, filmViewModel: FilmDBViewModel) { // filmViewModel: FilmViewModel
    val premieresState by viewModel.premieresState.collectAsState()
    val popularMoviesState by viewModel.popularMoviesState.collectAsState()

    val favoriteMoviesCount by filmViewModel.favoriteMoviesCount.collectAsState(0)  // Начальное значение 0
    Log.d("ProfilePage", "Favorite movies count: $favoriteMoviesCount")


    val watchLaterMoviesCount by filmViewModel.watchLaterMoviesCount.collectAsState(0)
    Log.d("ProfilePage", "watchLater movies count: $watchLaterMoviesCount")





    val isLoading = premieresState is ScreenState.Loading ||
            popularMoviesState is ScreenState.Loading

    if(isLoading){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

    }
    else{
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            Box(
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp)
                    .weight(1f)
            ){
                LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)) {
                    item {
                        CategorySection(
                            title = "Просмотрено",
                            state = premieresState,
                            onClickSeeAll = { navController.navigate("category/premieres") },
                            onClickMovie = { movieId -> navController.navigate("filmDetail/$movieId") }
                        )
                    }
                    item {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 20.dp)

                        ){
                            Text(
                                text = "Коллекции",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                text = "+ Создать свою коллекцию",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CollectionCard(
                                        name = "Любимые",
                                        itemCount =   favoriteMoviesCount, //"${filmViewModel.getFavoriteFilmsCount()}".toInt(),
                                        icon = Icons.Default.Favorite,
                                        modifier = Modifier.weight(1f).aspectRatio(1f),
                                        onClick = {navController.navigate("favoriteMoviesScreen")},
                                        navController

                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Log.d("CountFavoutite", "Count of films: $favoriteMoviesCount")
                                    CollectionCard(
                                        name = "Хочу посмотреть",
                                        itemCount = watchLaterMoviesCount, //"$watchlistCount".toInt()
                                        icon = Icons.Default.Add,
                                        modifier = Modifier.weight(1f).aspectRatio(1f),
                                        onClick = {navController.navigate("wanttowatchScreen")},
                                        navController
                                    )
                                    Log.d("CountWatchLater", "Count of films: $watchLaterMoviesCount")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    CollectionCard(
                                        name = "Русское кино",
                                        itemCount = 105,
                                        icon = Icons.Default.Person,
                                        modifier = Modifier.weight(1f) // Равный размер с карточками сверху
                                            .aspectRatio(1f),
                                        onClick = {}, navController
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

                    item {
                        CategorySection(
                            title = "Вам было интересно",
                            state = popularMoviesState,
                            onClickSeeAll = { navController.navigate("category/popular") },
                            onClickMovie = { movieId -> navController.navigate("filmDetail/$movieId") }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CollectionCard(name: String, itemCount: Int, icon: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit, navController: NavController) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Text(text = name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$itemCount", style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun CategorySection(
    title: String,
    state: ScreenState,
    onClickSeeAll: () -> Unit,
    onClickMovie: (Int) -> Unit
){
//    var isCleared by remember { mutableStateOf(false) }
//
//    if (isCleared || state is ScreenState.Error || state is ScreenState.Initial)
    when(state){
        is ScreenState.Success -> {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(end = 20.dp, bottom = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = state.movies.size.toString(),
                        fontSize = 15.sp,
                        color = Color.Blue,
                        modifier = Modifier.clickable { onClickSeeAll() }
                    )
                    Image(
                        painter = painterResource(R.drawable.rigtharrow),
                        contentDescription = "icons",
                        modifier = Modifier.size(18.dp)
                    )
                }
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(state.movies.take(8)) { movie ->
                        ItemView(
                            movie = movie,
                            onClick = { movieId -> onClickMovie(movieId) }
                        )
                    }
                    item {
                        DeleteButton() {

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
        is ScreenState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        is ScreenState.Initial ->{}
        else -> {}
    }
}

@Composable
fun DeleteButton(onShowAll: () -> Unit){
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
                modifier = Modifier.padding(top = 55.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ){
                Button(
                    onClick = onShowAll,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        //.padding(start = 20.dp, bottom = 10.dp)
                        .size(40.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.deleteicon),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(15.dp)
                            .clip(CircleShape)
                    )
                }
                Text(text = "Очистить историю", fontSize = 13.sp, modifier = Modifier.padding(start =  5.dp))
            }
        }
    }
}
