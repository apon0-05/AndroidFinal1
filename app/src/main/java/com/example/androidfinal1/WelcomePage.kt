package com.example.androidfinal1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

class WelcomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomePager(onSkip = {
                startActivity(Intent(this@WelcomePage, MainActivity::class.java))
                finish()
            })
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomePager(onSkip: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Top(onSkipClicked = onSkip)


        HorizontalPager(
            count = 3,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> onboardingScreen1()
                1 -> onboardingScreen2()
                2 -> onboardingScreen3()
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        )
    }
}


@Composable
fun Top(onSkipClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 25.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Skillcinema",
            fontSize = 24.sp,
        )

        TextButton(onClick = onSkipClicked) {
            Text(
                text = "Пропустить",
                color = Color(0xFFB5B5C9),
                fontSize = 14.sp,
                lineHeight = 15.sp
            )
        }
    }
}

// Содержимое первого экрана онбординга
@Composable
fun onboardingScreen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Body(R.drawable.img1, "Узнавай\nо премьерах")
    }
}

// Содержимое второго экрана онбординга
@Composable
fun onboardingScreen2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Body(R.drawable.img2, "Создавай коллекции")
    }
}

// Содержимое третьего экрана онбординга
@Composable
fun onboardingScreen3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Body(R.drawable.img3, "Делись\nс друзьями")
    }
}

// Компонент для отображения изображения и текста на экране
@Composable
fun Body(img: Int, text: String) {
    Spacer(modifier = Modifier.height(166.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(68.dp))
        Text(
            text = text,
            fontSize = 32.sp,
            modifier = Modifier.padding(end = 140.dp)
        )
    }
}
// Превью для экрана онбординга 1
@Preview(showBackground = true)
@Composable
fun OnboardingPreview1() {
    onboardingScreen1()
}

// Превью для экрана онбординга 2
@Preview(showBackground = true)
@Composable
fun OnboardingPreview2() {
    onboardingScreen2()
}

// Превью для экрана онбординга 3
@Preview(showBackground = true)
@Composable
fun OnboardingPreview3() {
    onboardingScreen3()
}