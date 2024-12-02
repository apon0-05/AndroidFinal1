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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidfinal1.R

@Composable
fun YearRangeSelectionScreen() {
    val yearRanges = listOf(
        1953 to 1964,
        1965 to 1976,
        1977 to 1988,
        1989 to 2000,
        2001 to 2012,
        2013 to 2024
    )

    var selectedStartRange by remember { mutableStateOf(yearRanges.last()) }
    var selectedEndRange by remember { mutableStateOf(yearRanges.last()) }
    var selectedStartYear by remember { mutableStateOf(selectedStartRange.first) }
    var selectedEndYear by remember { mutableStateOf(selectedEndRange.first) }

    fun updateStartRange(forward: Boolean) {
        val index = yearRanges.indexOf(selectedStartRange)
        if (forward && index < yearRanges.lastIndex) {
            selectedStartRange = yearRanges[index + 1]
            selectedStartYear = selectedStartRange.first
        } else if (!forward && index > 0) {
            selectedStartRange = yearRanges[index - 1]
            selectedStartYear = selectedStartRange.first
        }
    }

    fun updateEndRange(forward: Boolean) {
        val index = yearRanges.indexOf(selectedEndRange)
        if (forward && index < yearRanges.lastIndex) {
            selectedEndRange = yearRanges[index + 1]
            selectedEndYear = selectedEndRange.first
        } else if (!forward && index > 0) {
            selectedEndRange = yearRanges[index - 1]
            selectedEndYear = selectedEndRange.first
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
                    .clickable { }
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
                        text = "${selectedStartRange.first} - ${selectedStartRange.second}",
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
                    years = (selectedStartRange.first..selectedStartRange.second).toList(),
                    selectedYear = selectedStartYear,
                    onYearSelected = { year ->
                        selectedStartYear = year
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
                        text = "${selectedEndRange.first} - ${selectedEndRange.second}",
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
                    years = (selectedEndRange.first..selectedEndRange.second).toList(),
                    selectedYear = selectedEndYear,
                    onYearSelected = { year ->
                        selectedEndYear = year
                    },
                    modifier = Modifier.height(200.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {
                println("Выбранный период: с $selectedStartYear до $selectedEndYear")
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

@Preview(showBackground = true)
@Composable
fun PreviewYearRangeSelectionScreen() {
    YearRangeSelectionScreen()
}
