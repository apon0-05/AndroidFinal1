package com.example.androidfinal1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidfinal1.store.presentation.screen.ActorPage
import com.example.androidfinal1.store.presentation.navigation.BottomNavigationBar
import com.example.androidfinal1.store.presentation.screen.CategoryScreen
//import com.example.androidfinal1.store.presentation.FilmDetailPage
import com.example.androidfinal1.store.presentation.screen.FilmPage
import com.example.androidfinal1.store.presentation.screen.Filmography
import com.example.androidfinal1.store.presentation.screen.Gallery
import com.example.androidfinal1.store.presentation.screen.NewHomePage
import com.example.androidfinal1.store.presentation.navigation.Screen
import com.example.androidfinal1.store.presentation.screen.SearchPage
import com.example.androidfinal1.store.presentation.screen.SearchPreferences
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
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
                        composable("filmDetail/{id}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                                FilmPage(movieId = movieId,  navController)

                        }
                        composable("actorDetail/{id}") { backStackEntry ->
                            val actorId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                            ActorPage(actorId = actorId,  navController)


                        }
                        composable("filmmDetail/{id}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                            Gallery(movieId = movieId,  navController)

                        }
                        composable("actorFilms/{actorId}",
                            arguments = listOf(navArgument("actorId") { type = NavType.IntType }) // Указываем, что actorId — это Int
                        ) { backStackEntry ->
                            val actorId = backStackEntry.arguments?.getInt("actorId")
                            Log.d("actorINMain", "Clicked movie ID: ${actorId}")
                            Filmography(actorId = actorId, navController = navController)
                        }
                        composable(Screen.Search.route) {
                            SearchPage(viewModel, navController)
                        }
                        composable("search_preferences") {
                            SearchPreferences(navController)
                        }
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

//@Composable
//fun SearchPage(navController: NavController){
//
//}

@Composable
fun ProfilePage(navController: NavController){

}
