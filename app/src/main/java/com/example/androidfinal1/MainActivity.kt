package com.example.androidfinal1

import YearRangeSelectionScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.androidfinal1.store.presentation.screen.Filmography
import com.example.androidfinal1.store.presentation.components.ItemView
import com.example.androidfinal1.store.presentation.components.room.FilmDBViewModel
import com.example.androidfinal1.store.presentation.components.room.MovieDatabase
//import com.example.androidfinal1.store.presentation.components.room.AppDatabase
//import com.example.androidfinal1.store.presentation.components.room.FilmViewModel
import com.example.androidfinal1.store.presentation.screen.ActorPage
import com.example.androidfinal1.store.presentation.navigation.BottomNavigationBar
import com.example.androidfinal1.store.presentation.screen.CategoryScreen
//import com.example.androidfinal1.store.presentation.FilmDetailPage
import com.example.androidfinal1.store.presentation.screen.FilmPage
import com.example.androidfinal1.store.presentation.screen.Gallery
import com.example.androidfinal1.store.presentation.screen.NewHomePage
import com.example.androidfinal1.store.presentation.navigation.Screen
import com.example.androidfinal1.store.presentation.screen.CountryPage
import com.example.androidfinal1.store.presentation.screen.FavouritePage
import com.example.androidfinal1.store.presentation.screen.GenrePage
//import com.example.androidfinal1.store.presentation.screen.FavoriteFilmsPage
import com.example.androidfinal1.store.presentation.screen.ProfilePage
import com.example.androidfinal1.store.presentation.screen.SearchPage
//import com.example.androidfinal1.store.presentation.screen.SearchPage
import com.example.androidfinal1.store.presentation.screen.SearchPreferences
import com.example.androidfinal1.store.presentation.screen.WanttiwatchPage
import com.example.androidfinal1.store.presentation.screen.search.FilterViewModel
import com.example.androidfinal1.store.presentation.viewmodel.MoviesViewModel
import com.example.androidfinal1.store.presentation.viewmodel.ScreenState
import com.example.androidfinal1.ui.theme.AndroidFinal1Theme



class MainActivity : ComponentActivity() {
    private val db by lazy{
        Room.databaseBuilder(
            applicationContext, MovieDatabase::class.java,
            "movies.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    private val movieViewModel: FilmDBViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FilmDBViewModel(db.movieDao(), db.viewedMovieDao()) as T
            }
        }
    }


    private val viewModel: MoviesViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //applicationContext.deleteDatabase("movies.db")
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

                        val viewModel1: FilterViewModel = FilterViewModel()

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
                                FilmPage(movieId = movieId,  navController, movieViewModel) //filmViewModel

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
                            arguments = listOf(navArgument("actorId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val actorId = backStackEntry.arguments?.getInt("actorId")
                            Log.d("actorINMain", "Clicked movie ID: ${actorId}")
                            Filmography(actorId = actorId, navController = navController)
                        }



                        composable(Screen.Search.route) {
                            SearchPage( viewModel ,navController)
                        }

                        composable("search_preferences") {
                            SearchPreferences(navController, viewModel1)
                        }
                        composable(Screen.Profile.route) { ProfilePage(viewModel, navController, movieViewModel) } //filmViewModel
                        composable("favoriteMoviesScreen") {
                            FavouritePage(viewModel = movieViewModel, navController = navController)
                        }
                        composable("wanttowatchScreen"){
                            WanttiwatchPage(viewModel = movieViewModel, navController = navController)
                        }
                        composable("Страна"){
                            CountryPage(navController, viewModel1)
                        }
                        composable("Жанр"){
                            GenrePage(navController, viewModel1)
                        }
                        composable("Год"){
                            YearRangeSelectionScreen(navController, viewModel1)
                        }
                    }
                }
            }
        }

    }
}

//@Composable
//fun SearchPage(navController: NavController){
//
//}
