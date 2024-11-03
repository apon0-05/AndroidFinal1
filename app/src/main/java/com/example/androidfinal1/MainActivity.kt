package com.example.androidfinal1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidfinal1.store.presentation.BottomNavigationBar
import com.example.androidfinal1.store.presentation.CategoryScreen
import com.example.androidfinal1.store.presentation.NewHomePage
import com.example.androidfinal1.store.presentation.Screen
import com.example.androidfinal1.store.viewmodel.MoviesViewModel
import com.example.androidfinal1.ui.theme.AndroidFinal1Theme

class MainActivity : ComponentActivity() {
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidFinal1Theme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            NewHomePage(viewModel, navController)
                        }
                        composable("category/{categoryIndex}") { backStackEntry ->
                            val categoryIndex = backStackEntry.arguments?.getString("categoryIndex")?.toInt()
                            if (categoryIndex != null) {
                                CategoryScreen(categoryIndex = categoryIndex, navController)
                            }
                        }
                        composable(Screen.Search.route) { SearchPage(navController) }
                        composable(Screen.Profile.route) { ProfilePage(navController) }
                    }
                }
            }
        }

        // Uncomment and call these functions in your ViewModel as needed
        // viewModel.fetchPremieres(2024, "JANUARY")
        // viewModel.fetchCollections("TOP_POPULAR_MOVIES", 1)
        // viewModel.fetchZombie("ZOMBIE_THEME", 1)
    }
}

@Composable
fun SearchPage(navController: NavController){

}

@Composable
fun ProfilePage(navController: NavController){

}
