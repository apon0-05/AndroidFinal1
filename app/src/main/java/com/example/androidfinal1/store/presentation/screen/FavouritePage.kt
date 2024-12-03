package com.example.androidfinal1.store.presentation.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidfinal1.store.presentation.components.room.FilmDBViewModel
import com.example.androidfinal1.store.presentation.components.room.MovieEntity
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.androidfinal1.R
import com.example.androidfinal1.store.data.remote.Movie

@Composable
fun FavouritePage(
    viewModel: FilmDBViewModel,
    navController: NavController
){


    val favoriteMovies by viewModel.favoriteMovies.collectAsState(initial = emptyList())

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
                text = "Любимые",
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
                    .clickable { viewModel.deleteAllFavoriteMovies()},
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
                items(favoriteMovies) { movie ->
                    ItemViewUwU(movie = movie, onClick = {

                         navController.navigate("filmDetail/${movie.id}")
                    })
                }
            }
        }
    }



}


@Composable
fun MovieCard(movie: MovieEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),

    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(movie.posterUrl),
                contentDescription = "Movie Poster",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = movie.nameRu!!, )
                Text(text = "Рейтинг: ${movie.rating}", )
            }
        }
    }
}


@Composable
fun ItemViewUwU(
    movie: MovieEntity,
    //onItemClick: (String) -> Unit,
    onClick: (Int) -> Unit
    //isLastItem: Boolean,

) {
    Box(
        modifier = Modifier
        //     .clickable { onItemClick(movie.id.toString()) }
    ) {
        Column(
            modifier = Modifier.clickable { movie.id?.let { onClick(it) } }
        ) {

            movie.posterUrl?.let { posterUrl ->
                Box(
                    modifier = Modifier
                        .size(width = 130.dp, height = 194.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { movie.id?.let { onClick(it)} /* action */ } //movie.id?.let { onClick(it)}

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


        }


    }


}