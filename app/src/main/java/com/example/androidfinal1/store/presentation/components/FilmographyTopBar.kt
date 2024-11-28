package com.example.androidfinal1.store.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidfinal1.R


@Composable
fun FilmographyTopBar(actorName: String, navController: NavController){
    TopAppBar(
        backgroundColor = Color.White,
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = 3.dp)
        ) {
            IconButton(onClick = { navController.popBackStack()}) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.width(68.dp))
            Text(
                text = "Фильмография",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
    Text(
        text = actorName,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
        modifier = Modifier
            .padding(horizontal = 26.dp, vertical = 8.dp)
    )

}