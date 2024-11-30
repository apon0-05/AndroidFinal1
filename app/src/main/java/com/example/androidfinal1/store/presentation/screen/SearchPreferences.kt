package com.example.androidfinal1.store.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.components.FilmCard
import com.example.androidfinal1.store.presentation.components.ShowContent
import androidx.compose.foundation.layout.Row as Row1

@Composable
fun SearchPreferences() {
    val tabs = listOf("Все", "Фильмы", "Сериалы")
    val tabs2 = listOf("Дата", "Популярность", "Рейтинг")

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .padding(26.dp),
                horizontalArrangement = Arrangement.spacedBy(92.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "not watched list",
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(text = "Настройки поиска")
            }
        }
        item {
            ShowContent(
                tabs = tabs,
                title = "Показывать"
            )
        }
        item {
            Sorting("Страна","Россия")
            CustomDivider()
            Sorting("Жанр","Комедия")
            CustomDivider()
            Sorting("Год","с 1997 до 2017")
            CustomDivider()
            Sorting("Рейтинг","любой")
        }
        item {
            RangeSliderExample()
            CustomDivider()
        }
        item {
            ShowContent(
                tabs = tabs2,
                title = "Сортировать"
            )
        }
        item {
            CustomDivider()
            NotWatched()
        }
    }
}

@Composable
fun Sorting(category: String, type: String, modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(16.dp))

    Row1(
        modifier = modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = category)
        Text(text = type, color = Color.Gray)
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun CustomDivider(modifier: Modifier = Modifier) {
    Divider(
        color = Color.Gray,
        modifier = Modifier
            .height(0.5.dp)
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RangeSliderExample() {
    var sliderValues by remember { mutableStateOf(1f..10f) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RangeSlider(
            value = sliderValues,
            onValueChange = { newValues -> sliderValues = newValues },
            valueRange = 1f..10f, // Диапазон от 1 до 10
            steps = 0, // Ограниечение на количество делений между ползунками
            onValueChangeFinished = { /* По завершению изменения */ },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.Blue,
                inactiveTrackColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Отображаем значения ползунков
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${sliderValues.start.toInt()}", color = Color.Gray)
            Text(text = "${sliderValues.endInclusive.toInt()}", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun NotWatched(modifier: Modifier = Modifier) {
    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.eye),
                contentDescription = "not watched list",
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Не просмотрен")

        }
        Spacer(modifier = Modifier.height(16.dp))
        FilmCard()
    }
}

@Composable
@Preview(showBackground = true)
fun SearchPrefPreview() {
    SearchPreferences()
}