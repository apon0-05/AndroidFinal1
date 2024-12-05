package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import com.example.androidfinal1.store.presentation.screen.search.FilterViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidfinal1.R
import com.example.androidfinal1.store.data.remote.MovieId
import com.example.androidfinal1.store.presentation.components.FilmCard
import com.example.androidfinal1.store.presentation.components.ShowContent
import androidx.compose.foundation.layout.Row as Row1

@Composable
fun SearchPreferences(navController: NavController, filterViewModel: FilterViewModel = viewModel()) {

    val filters by filterViewModel.filters.collectAsState()

    LaunchedEffect(filters.country) {
        Log.d("SearchPreferences", "Selected country: ${filters.country}")
    }

    val tabs = listOf("Все", "Фильмы", "Сериалы")
    val tabs2 = listOf("Дата", "Популярность", "Рейтинг")

    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .padding(26.dp),
                horizontalArrangement = Arrangement.spacedBy(86.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Image(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "not watched list",
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
                Text(text = "Настройки поиска")
            }
        }
        item {
            ShowContent(
                tabs = tabs,
                title = "Показывать",
                selectedTab = filters.showType,
                onTabSelected = { selectedTab ->
                    filterViewModel.updateShowType(selectedTab) // Обновление через ViewModel
                }
            )
        }
        item {
            Sorting("Страна",filters.country ?: "Не выбрано", onClick = {navController.navigate("Страна")})
            Log.d("SearchPreferences", "Country Clicked: ${filters.country}")
            CustomDivider()
            Sorting("Жанр",filters.genre ?: "Не выбрано", onClick = {navController.navigate("Жанр")})
            CustomDivider()
            Sorting("Год",
                filters.startYear?.let { start ->
                    filters.endYear?.let { end ->
                        "с $start до $end"
                    } ?: "с $start до любой"
                } ?: "любой",
                onClick = { navController.navigate("Год") }
            )
            CustomDivider()
            Sorting("Рейтинг",filters.ratingRange?.let { "от ${it.first} до ${it.second}" } ?: "любой", onClick = {})
        }
        item {
            RangeSliderExample(filterViewModel)
            CustomDivider()
        }
        item {
            ShowContent(
                tabs = tabs2,
                title = "Сортировать",
                selectedTab = filters.sortBy,
                onTabSelected = { selectedTab ->
                    filterViewModel.updateSortBy(selectedTab) // Обновление через ViewModel
                }
            )
        }
//        item {
//            CustomDivider()
//            NotWatched()
//        }
    }
}

@Composable
fun Sorting(category: String, type: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(16.dp))

    Row1(
        modifier = modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth(1f),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = category, modifier = Modifier.clickable { onClick() })
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
fun RangeSliderExample(filterViewModel: FilterViewModel) {
    val filters by filterViewModel.filters.collectAsState()

    var sliderValues by remember { mutableStateOf(1f..10f) } // Диапазон значений слайдера

    // Обновляем ratingRange в ViewModel, когда ползунок изменяется
    val onSliderValueChange = { newValues: ClosedFloatingPointRange<Float> ->
        sliderValues = newValues
        filterViewModel.updateRatingRange(newValues.start to newValues.endInclusive)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RangeSlider(
            value = sliderValues,
            onValueChange = { newValues ->
                sliderValues = newValues
                // Обновление диапазона рейтинга в ViewModel
                filterViewModel.updateRatingRange(newValues.start to newValues.endInclusive)
            },
            valueRange = 1f..10f, // Диапазон значений слайдера от 1 до 10
            steps = 0,
            onValueChangeFinished = { /* Действия после завершения изменения */ },
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
fun NotWatched(movies: List<MovieId>, modifier: Modifier = Modifier) {
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
        LazyColumn {
            items(movies) { movie ->
                FilmCard(movie = movie)
            }
        }
    }
}

