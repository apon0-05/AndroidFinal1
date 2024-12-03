package com.example.androidfinal1

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isInternetAvailable = isNetworkAvailable(this)
            WelcomePager(
                isInternetAvailable = isInternetAvailable,
                onSkip = {

                    startActivity(Intent(this@WelcomePage, MainActivity::class.java))
                    finish()
                }
            )
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomePager(isInternetAvailable: Boolean, onSkip: () -> Unit) {
    if (!isInternetAvailable) {
        NoInternetScreen()
        return
    }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Top(onSkipClicked = {
            coroutineScope.launch {
                onSkip()
            }
        })

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
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}


@Composable
fun NoInternetScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        }
            Spacer(modifier = Modifier.height(250.dp))
            Image(
                painter = painterResource(id = R.drawable.img1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(68.dp))
            Text(
                text = "Some troubles with connection",
                fontSize = 16.sp,
                color = Color.Red,
                textAlign = TextAlign.Center
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

@Preview(showBackground = true)
@Composable
fun OnboardingPreview1() {
    onboardingScreen1()
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview2() {
    onboardingScreen2()
}


@Preview(showBackground = true)
@Composable
fun OnboardingPreview3() {
    onboardingScreen3()
}

