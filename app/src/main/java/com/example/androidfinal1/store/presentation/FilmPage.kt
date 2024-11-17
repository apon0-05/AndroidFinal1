package com.example.androidfinal1.store.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidfinal1.R

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
fun FilmPage() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Блок информации о фильме
        MovieInfo()

        // Блок с описанием о фильме
        Description()

        // Секция: В фильме снимались
        SectionTitle(title = "В фильме снимались", count = 27)
        CastList()

        // Секция: Над фильмом работали
        SectionTitle(title = "Над фильмом работали", count = 19)
        CrewList()

        // Секция: Галерея
        SectionTitle(title = "Галерея", count = 21)
        Gallery()

        // Секция: Похожие фильмы
        SectionTitle(title = "Похожие фильмы", count = 4)
        SimilarMovies()

    }
}



@Composable
fun MovieInfo() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x1B1B1B00),
                        Color(0xFF1B1B1B)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(0f, 1050f),
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.caretleft),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(26.dp)
                .size(24.dp)
        )

        // Картинка фильма
//        Image(
//            painter = painterResource(id = R.drawable.some_movie_image),
//            contentDescription = "Movie Poster",
//            modifier = Modifier
//                .align(Alignment.Center)
//                .size(200.dp) // Размер картинки
//        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.image1),
                contentDescription = "film mini image"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "6.4 Китобой",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = "2020, драма, приключения",
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = "Россия, 1 ч 33 мин, 16+",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(13.dp) // Делаем пространство между иконками
            ) {
                IconsHolder(R.drawable.icons)
                IconsHolder(R.drawable.icons2)
                IconsHolder(R.drawable.icons3)
                IconsHolder(R.drawable.icons4)
                IconsHolder(R.drawable.icons5)
            }

        }
    }
}

@Composable
fun IconsHolder(img: Int) {
    Box(
        modifier = Modifier
            .size(25.dp)
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = "icons",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun Description() {
    Column (
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(26.dp)
    ){
        Text(
            text = "Чукотский парень влюбляется в американскую вебкам-модель. Приз Венеции, Кристина Асмус в роли девушки мечты",
            fontSize = 16.sp,
            fontWeight = Bold

        )
        Text(
            text = "Все меняется в жизни юного чукотского охотника Лёшки с появлением в поселке интернета. Он влюбляется — впервые и сильно — в молчаливую девушку...",
            fontSize = 16.sp
        )
    }
}


@Composable
fun SectionTitle(title: String, count: Int) {
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
            color = Color.Blue
        )
        Image(
            painter = painterResource(R.drawable.rigtharrow),
            contentDescription = "icons",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun CastList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(5) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(4) { index ->
                    InfoCards("Владимир Онохов", "Лешка")
                }
            }
        }
    }
}

@Composable
fun InfoCards(name: String, role: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.a4),
                contentDescription = "Cast Member",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Column {
                Text(text = name, fontSize = 12.sp)
                Text(text = role, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun CrewList() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(3) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(2) { index ->
                    InfoCards("Владимир Онохов", "Лешка")
                }
            }
        }
    }
}

@Composable
fun Gallery() {
    LazyRow {
        items(3) {
            Image(
                painter = painterResource(id = R.drawable.a4_2),
                contentDescription = "Gallery Image",
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun SimilarMovies() {

}



//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    FilmPage()
//}