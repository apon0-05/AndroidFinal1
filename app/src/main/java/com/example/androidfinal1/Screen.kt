package com.example.androidfinal1

sealed class Screen(val route: String, val icon: Int){
    object Home: Screen("home", R.drawable.home)
    object Search: Screen("search", R.drawable.search)
    object Profile: Screen("profile", R.drawable.profile)
}
