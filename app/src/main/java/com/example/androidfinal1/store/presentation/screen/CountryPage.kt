package com.example.androidfinal1.store.presentation.screen

import android.util.Log
import com.example.androidfinal1.store.presentation.screen.search.FilterViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidfinal1.R
import kotlinx.coroutines.delay


@Composable
fun CountryPage(navController: NavController, filterViewModel: FilterViewModel = viewModel()) {
    val countries = listOf("Россия", "Великобритания", "Германия", "США", "Франция")

    val filters by filterViewModel.filters.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
   // var selectedCountry by remember { mutableStateOf<String?>(null) }

    val filteredCountries = countries.filter {
        it.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .padding(bottom = 15.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(13.dp)
                    .clickable {  navController.popBackStack() }
            )
            Spacer(modifier = Modifier.width(115.dp))
            Text(
                text = "Страна",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            )
        }

        CountrySearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            hintText = "Введите страну..."
        )


        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            if (filteredCountries.isEmpty()) {
                Text(
                    text = "Нет совпадений",
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                Log.d("CountyPage", "Country Clicked: ${filters.country}")
                filteredCountries.forEach { country ->
                    CountryRow(
                        country = country,
                        isSelected = country == filters.country,

                        onClick = {
                            filterViewModel.updateCountry(country)
                            Log.d("BeforeBack", "Country Clicked: ${filters.country}")
                          //  navController.popBackStack()
                        }

                    )
                }
                Log.d("AfterBack", "Country Clicked: ${filters.country}")
            }
        }
        Log.d("Goooo", "Country Clicked: ${filters.country}")
    }
}

@Composable
fun CountrySearchBar(query: String, onQueryChange: (String) -> Unit, hintText: String) {
    var debouncedQuery by remember { mutableStateOf(query) }


    LaunchedEffect(query) {
        delay(500)
        debouncedQuery = query
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = hintText,
                            style = TextStyle(fontSize = 18.sp, color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }

    LaunchedEffect(debouncedQuery) {
        if (debouncedQuery.isNotEmpty()) {
//            fetchCountries(debouncedQuery)
        }
    }
}



@Composable
fun CountryRow(country: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                if (isSelected) Color(0xFFE0E0E0) else Color.Transparent,
                shape = RoundedCornerShape(0.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = country,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal
            )
        )
    }
}
