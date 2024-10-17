package com.example.androidfinal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidfinal1.ui.theme.AndroidFinal1Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidFinal1Theme {
                whatis()
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun whatis(){
    val navController = rememberNavController()
    newHomePage(navController)
}

@Composable
fun newHomePage(navController: NavController){
    val list = listOf(
        Films(4.3, "image", "Близкие", "драма", ),
        Films(4.3, "image", "Близкие", "драма", ),
        Films(4.3, "image", "Близкие", "драма", ),
        Films(4.3, "image", "Близкие", "драма", ),
        Films(4.3, "image", "Близкие", "драма", )
    )
    val lgenre = listOf("Премьеры", "Популярное", "Боевики")


    Text(
        text = "Skillcinema",
        modifier = Modifier.padding(top = 90.dp, start = 20.dp),
        fontSize = 23.sp
        )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .padding(top = 150.dp, start = 20.dp)
                .weight(1f)
               // .fillMaxSize()
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                items(items = lgenre) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 20.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ) {
                        Text(text = item, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Все", fontSize = 15.sp, color = Color.Blue)
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(items = list) { index, item ->
                            val isLastItem = index == list.lastIndex
                            ItemView(item = item, isLastItem = isLastItem)

                        }
                    }
                }
            }
        }

        // Non-scrollable navigation row at the bottom
//        Box(
//            modifier = Modifier
//                .size(400.dp, 60.dp)
//                .background(
//                    Color(0x3D3BFF).copy(alpha = 0.03f),
//                    shape = RoundedCornerShape(16.dp)),
//            contentAlignment = Alignment.Center
//
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(400.dp, 60.dp)
//                    .padding(top = 3.dp)
//                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
//                contentAlignment = Alignment.Center
//            ){

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//
//                ) {
//                    Box(
//                        modifier = Modifier.size(20.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.home),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize()
//
//                        )
//
//                    }
//
//                    Box(
//                        modifier = Modifier.size(20.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.search),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    }
//                    Box(
//                        modifier = Modifier.size(20.dp)
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.profile),
//                            contentDescription = null,
//                            modifier = Modifier.fillMaxSize()
//                        )
//
//                    }
//
//                }


          //  }

       // }
    }
}



@Composable
fun ItemView(
    item: Films,
    isLastItem: Boolean
){
    Box(){
        if(isLastItem){
            Box(
                modifier = Modifier.size(width = 130.dp, height = 194.dp)
                    .background(
                        color = Color(0x66B5B5C9),
                        shape = RoundedCornerShape(
                            topStart = 4.dp,
                            topEnd = 4.dp,
                            bottomEnd = 4.dp,
                            bottomStart = 4.dp
                        ))
                    .clip(RoundedCornerShape(10.dp)),
            ){
                Column(
                    modifier = Modifier.padding(top = 55.dp, start = 25.dp)
                ){
                        Button(
                            onClick = {
                                //action
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .padding(start = 20.dp, bottom = 10.dp)
                                .size(40.dp)


                        ){
                            Image(
                                painter = painterResource(id = R.drawable.icon),
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .size(15.dp)
                                    .clip(CircleShape)
                            )

                        }
                        Text(text = "Показать все", fontSize = 13.sp)
                    }
            }

        }
        else{
            Column {
                Box(

                    modifier = Modifier
                        .size(width = 130.dp, height = 194.dp)
                        .background(
                            color = Color(0x66B5B5C9),
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp,
                                bottomEnd = 4.dp,
                                bottomStart = 4.dp
                            ))
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            //action
                        },
                        contentAlignment = Alignment.TopEnd
                    ){

                    Text(text = item.rating.toString(),
                        fontSize = 7.sp,
                        lineHeight = 7.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 6.dp, end = 6.dp)
                            .background(
                                color = Color.Blue,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 5.dp, vertical = 2.dp)

                        )

                }
                Text(text = item.name, fontSize = 20.sp, modifier = Modifier.padding(top = 5.dp))
                Text(text = item.genre, fontSize = 15.sp, color = Color.Gray)
            }

        }

    }

}
data class Films(
    val rating: Double,
    val image: String,
    val name: String,
    val genre: String,


)
data class Title(
    val title: String
)

val list = listOf(
    Films(4.3, "image", "Avatar", "drama",)
)


sealed class Screen(val route: String, val icon: Int){
    object Home: Screen("home", R.drawable.home)
    object Search: Screen("search", R.drawable.search)
    object Profile: Screen("profile", R.drawable.profile)
}

@Composable
fun SearchPage(navController: NavController){

}

@Composable
fun ProfilePage(navController: NavController){

}