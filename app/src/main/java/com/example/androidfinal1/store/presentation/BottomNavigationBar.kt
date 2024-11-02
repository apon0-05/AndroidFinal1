package com.example.androidfinal1.store.presentation


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidfinal1.R


@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(Screen.Home, Screen.Search, Screen.Profile)
    val currentRoute = navController.currentBackStackEntry?.destination?.route


    Box(
        modifier = Modifier
            .size(500.dp, 90.dp)
            .background(
                Color(0x3D3BFF).copy(alpha = 0.03f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(500.dp, 90.dp)
                .padding(top = 3.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {


            NavigationBar(
                modifier = Modifier.padding(horizontal = 50.dp),
                containerColor = Color.White, // Делает фон NavigationBar прозрачным, чтобы он унаследовал фон содержащего Box
                tonalElevation = 0.dp
            ) {
//                val items = listOf(Screen.Home, Screen.Search, Screen.Profile)
//                val currentRoute = navController.currentBackStackEntry?.destination?.route

                items.forEach { screen ->
                    val isSelected = currentRoute == screen.route

                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = screen.icon),
                                contentDescription = screen.route,
                                modifier = Modifier.size(24.dp),
                                tint = if (isSelected) Color.Blue else Color.Gray
                            )
                        },
                        selected = isSelected,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = Color.Gray
                        ),
                        alwaysShowLabel = false,
                        // Update with actual selection logic
                        onClick = {
                            if(!isSelected) {
                                Log.d("Navigation", "Navigating to: ${screen.route}")
                                navController.navigate(screen.route){
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
//                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = Color.Blue,
//                            unselectedIconColor = Color.Gray
//                        )
                    )
                }
            }
        }
    }
}
