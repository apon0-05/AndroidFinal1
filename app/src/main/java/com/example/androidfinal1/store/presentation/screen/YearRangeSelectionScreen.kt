import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidfinal1.R
import com.example.androidfinal1.store.presentation.screen.search.FilterViewModel

@Composable
fun YearRangeSelectionScreen(navController: NavController, filterViewModel: FilterViewModel = viewModel()) {
    val yearRanges = listOf(
        1953 to 1964,
        1965 to 1976,
        1977 to 1988,
        1989 to 2000,
        2001 to 2012,
        2013 to 2024
    )

    val filters by filterViewModel.filters.collectAsState()

    val selectedStartRange = filters.startYear ?: yearRanges.last().first
    val selectedEndRange = filters.endYear ?: yearRanges.last().second

    fun updateStartRange(forward: Boolean) {
        val index = yearRanges.indexOfFirst { it.first == selectedStartRange }
        if (forward && index < yearRanges.lastIndex) {
            val newRange = yearRanges[index + 1]
            filterViewModel.updateStartYear(newRange.first)
        } else if (!forward && index > 0) {
            val newRange = yearRanges[index - 1]
            filterViewModel.updateStartYear(newRange.first)
        }
    }

    fun updateEndRange(forward: Boolean) {
        val index = yearRanges.indexOfFirst { it.first == selectedEndRange }
        if (forward && index < yearRanges.lastIndex) {
            val newRange = yearRanges[index + 1]
            filterViewModel.updateEndYear(newRange.first)
        } else if (!forward && index > 0) {
            val newRange = yearRanges[index - 1]
            filterViewModel.updateEndYear(newRange.first)
        }
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
                    .clickable { navController.popBackStack()  }
            )
            Spacer(modifier = Modifier.width(115.dp))
            Text(
                text = "Период",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Искать в период с",
            style = TextStyle(fontSize = 18.sp, color = Color(0xFF838390))
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${selectedStartRange} - ${selectedEndRange}",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Blue),
                    )
                    Spacer(modifier = Modifier.width(180.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(13.dp)
                            .clickable { updateStartRange(false) }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.rigtharrow),
                        contentDescription = "Right Arrow",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { updateStartRange(true) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                YearTable(
                    years = (selectedStartRange..selectedEndRange).toList(),
                    selectedYear = selectedStartRange,
                    onYearSelected = { year ->
                        filterViewModel.updateStartYear(year)
                    },
                    modifier = Modifier.height(200.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Искать в период до",
            style = TextStyle(fontSize = 18.sp, color = Color(0xFF838390))
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${selectedEndRange} - ${selectedEndRange}",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Blue),
                    )
                    Spacer(modifier = Modifier.width(180.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(13.dp)
                            .clickable { updateEndRange(false) }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.rigtharrow),
                        contentDescription = "Right Arrow",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { updateEndRange(true) }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                YearTable(
                    years = (selectedStartRange..selectedEndRange).toList(),
                    selectedYear = selectedEndRange,
                    onYearSelected = { year ->
                        filterViewModel.updateEndYear(year)
                    },
                    modifier = Modifier.height(200.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {
                println("Выбранный период: с $selectedStartRange до $selectedEndRange")
            },
            modifier = Modifier
                .padding(start = 120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Выбрать",
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun YearTable(
    years: List<Int>,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val columns = 3

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        items(years) { year ->
            val isSelected = selectedYear == year
            val backgroundColor = if (isSelected) Color(0xFFADD8E6) else Color.Transparent

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .clickable { onYearSelected(year) }
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = year.toString(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewYearRangeSelectionScreen() {
//    YearRangeSelectionScreen()
//}
