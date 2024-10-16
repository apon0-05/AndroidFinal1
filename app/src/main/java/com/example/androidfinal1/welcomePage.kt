package com.example.androidfinal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*


class welcomePage: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            onboardingScreen1()
            onboardingScreen2()
            onboardingScreen3()
        }
    }
}

@Composable
fun onboardingScreen1() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Top()
        Body(R.drawable.img1, "Узнавай\nо премьерах")

    }
}

@Composable
fun onboardingScreen2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Top()
        Body(R.drawable.img2, "Создавай коллекции")

    }
}

@Composable
fun onboardingScreen3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Top()
        Body(R.drawable.img3, "Делись\nс друзьями")
    }
}

@Composable
fun Top() {
    // Верхняя часть с заголовком и кнопкой "Пропустить"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Skillcinema",
            fontSize = 24.sp,
        )

        TextButton(onClick = {

        }) {
            Text(
                text = "Пропустить",
                color = Color(0xFFB5B5C9),
                fontSize = 14.sp,
                lineHeight = 15.sp
            )
        }
    }
}

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
            modifier = Modifier.padding(end=140.dp)
        )
    }
}

@Preview(showBackground = true, name = "Onboarding Screen 1")
@Composable
fun onboardingPreview1() {
    onboardingScreen1()
}

@Preview(showBackground = true, name = "Onboarding Screen 2")
@Composable
fun onboardingPreview2() {
    onboardingScreen2()
}

@Preview(showBackground = true, name = "Onboarding Screen 3")
@Composable
fun onboardingPreview3() {
    onboardingScreen3()
}
