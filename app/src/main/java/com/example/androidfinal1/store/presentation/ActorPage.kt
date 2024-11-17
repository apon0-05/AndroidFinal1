package com.example.androidfinal1.store.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
//                ActorPage()
//            }
//        }
//    }
//}


@Composable
fun ActorPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.caretleft),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(26.dp)
                .size(24.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.a4_1),
                    contentDescription = "actor picture",
                    modifier = Modifier
                        .size(150.dp)
                )
                Column {
                    Text(text = "Кристина Асмус", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Актриса", fontSize = 12.sp, color = Color.Gray)
                }
            }
            SecTitle("Лучшее", "Все", "")
            SecTitle("Фильмография", "К списку", "44 фильма")
        }
    }
}

@Composable
fun SecTitle(title: String, text: String, filmCount: String) {
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
            Text(
                text = text,
                fontSize = 14.sp,
                color = Color.Blue
            )
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActorPage()
}