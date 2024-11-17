package com.example.androidfinal1.store.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidfinal1.R
import com.example.androidfinal1.store.viewmodel.MoviesViewModel


data class FilmState(
   // val filmInfo: FilmInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


sealed class FilmIntent {
    object LoadFilm : FilmIntent()  // Загружает информацию о фильме
    data class OpenActorPage(val actorId: Int) : FilmIntent()  // Переход на ActorPage
}


class FilmViewModel : ViewModel() {

    private val _state = MutableStateFlow(FilmState())
    val state: StateFlow<FilmState> = _state

    fun handleIntent(intent: FilmIntent) {
        when (intent) {
            is FilmIntent.LoadFilm -> loadFilmInfo()
            is FilmIntent.OpenActorPage -> openActorPage(intent.actorId)
        }
    }

    private fun loadFilmInfo() {
        // Логика API запроса и обновление состояния
        // _state.value = _state.value.copy(isLoading = true)
    }

    private fun openActorPage(actorId: Int) {
        // Навигация к ActorPage с actorId
    }
}




data class ImageData(
    val int: Int
)

@Composable
fun TextInfoFilm(
    text: String,
    color: Color = Color(0xFFB5B5C9),
    fontSize: TextUnit = 16.sp, // Default font size
    padding: Dp = 5.dp // Def
){
    Text(
        text = text,
        modifier = Modifier
            .padding(padding),
        color = color,
        fontSize = fontSize
    )

}



@Preview(showBackground = true)
@Composable
fun ShowPrev(){

    val iconList = listOf(
        ImageData(R.drawable.icon),
        ImageData(R.drawable.icon__1_),
        ImageData(R.drawable.icon__2_),
        ImageData(R.drawable.icon__3_),
        ImageData(R.drawable.icon__4_)
    )


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(width = 400.dp, height = 400.dp)
                .clickable { /* action */ }
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x001B1B1B),  // Цвет #1B1B1B с непрозрачностью 100%
                            Color(0xFF1B1B1B),// Цвет #1B1B1B с прозрачностью 0%
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )

        ) {
            Image(
                painter = painterResource(id = R.drawable.iconfilmpage),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(25.dp)
                    .size(15.dp)
            )
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).padding(30.dp)
            ) {
                Column {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 10.dp)

                    ) {
                        Text(
                            text = "NameOffilm",
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
                            color = Color(0xFFB5B5C9),
                            fontSize = 35.sp

                        )
                        Text(
                            text = "rating + name in rus",
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
                            color = Color(0xFFB5B5C9)
                        )
                        Text(
                            text = "year + genre",
                            modifier = Modifier.padding(5.dp) .align(Alignment.CenterHorizontally),
                            color = Color(0xFFB5B5C9)
                        )
                        Text(
                            text = "country + time + age",
                            modifier = Modifier.padding(5.dp).align(Alignment.CenterHorizontally),
                            color = Color(0xFFB5B5C9)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        iconList.forEach { iconData ->
                            Image(
                                painter = painterResource(iconData.int),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                }
            }


//                Image(
//                    painter = rememberImagePainter(data = posterUrl),
//                    contentDescription = "Movie Poster",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize()
//                )
        }

        Box(
            modifier = Modifier.weight(1f)

        ) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(25.dp)){
                
            }


        }



    }

}



@Composable
fun FilmPage(viewModel: FilmViewModel) {
    val state by viewModel.state.collectAsState()

    // UI отображение данных фильма и обработка нажатий на актеров
    if (state.isLoading) {
        // Показать индикатор загрузки
    } else {
        // Показать информацию о фильме
       // Text(text = "Title: ${state.filmInfo?.title}")

        // Список актеров

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(width = 360.dp, height = 400.dp)
                    .clickable { /* action */ }

            ) {
//                Image(
//                    painter = rememberImagePainter(data = posterUrl),
//                    contentDescription = "Movie Poster",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.matchParentSize()
//                )
            }



        }

    }
}

