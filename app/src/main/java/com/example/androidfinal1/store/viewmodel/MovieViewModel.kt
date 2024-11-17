package com.example.androidfinal1.store.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal1.store.data.remote.Actor
import com.example.androidfinal1.store.data.remote.KinopoiskApi
import com.example.androidfinal1.store.data.remote.Movie
import com.example.androidfinal1.store.data.remote.MovieId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface ScreenState {
    object Initial : ScreenState
    object Loading : ScreenState
    data class Success(val movies: List<Movie>) : ScreenState
    data class Error(val message: String) : ScreenState

    object FilmLoading : ScreenState
    data class FilmSuccess(val movie: MovieId) : ScreenState
    data class FilmError(val message: String) : ScreenState

    object ActorLoading: ScreenState
    data class ActorSuccess(val actor: Actor): ScreenState
    data class ActorError(val message: String): ScreenState

}

class MoviesViewModel : ViewModel() {
    private val _premieresState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val premieresState: StateFlow<ScreenState> get() = _premieresState

    private val _popularMoviesState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val popularMoviesState: StateFlow<ScreenState> get() = _popularMoviesState

    private val _zombieMoviesState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val zombieMoviesState: StateFlow<ScreenState> get() = _zombieMoviesState

    private val _familyMoviesState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val familyMoviesState: StateFlow<ScreenState> get() = _familyMoviesState


    private val _filmDetailsState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val filmDetailsState: StateFlow<ScreenState> get() = _filmDetailsState

    private val _actorState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val actorState: StateFlow<ScreenState> get() = _actorState


    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            fetchPremieres(2024, "JANUARY")
            fetchCollections("TOP_POPULAR_MOVIES", 1)
            fetchZombies()
            //fetchFamily()
        }
    }

    private suspend fun fetchPremieres(year: Int, month: String) {
        _premieresState.value = ScreenState.Loading
        try {
            val response = KinopoiskApi.retrofitService.getPremieres(year, month)
            if (response.isSuccessful) {
                _premieresState.value = ScreenState.Success(response.body()?.movies ?: emptyList())
            } else {
                _premieresState.value = ScreenState.Error("Failed to fetch premieres")
            }
        } catch (e: Exception) {
            _premieresState.value = ScreenState.Error("Network error")
        }
    }

    private suspend fun fetchCollections(type: String, page: Int){
        _popularMoviesState.value = ScreenState.Loading
        try{
            val response = KinopoiskApi.retrofitService.getCollections(type, page)
            if(response.isSuccessful){
                _popularMoviesState.value = ScreenState.Success(response.body()?.movies ?: emptyList())
            }
            else{
                _popularMoviesState.value = ScreenState.Error("Failed to fetch premieres")
            }
        } catch (e: Exception) {
            _premieresState.value = ScreenState.Error("Network error")
        }

    }

    private suspend fun fetchZombies(){
        _zombieMoviesState.value = ScreenState.Loading
        try{
            val response = KinopoiskApi.retrofitService.getZombie()
            if(response.isSuccessful){
                _zombieMoviesState.value = ScreenState.Success(response.body()?.movies ?: emptyList())
            }
            else{
                _zombieMoviesState.value = ScreenState.Error("Failed to fetch premieres")
            }
        } catch (e: Exception) {
            _zombieMoviesState.value = ScreenState.Error("Network error")
        }

    }


//    fun getFilmDetails(movieId: String) {
//        viewModelScope.launch {
//            _filmDetailsState.value = ScreenState.FilmLoading
//            try {
//                val response = KinopoiskApi.retrofitService.getMovieDetails(movieId)
//                if (response.isSuccessful) {
//                    _filmDetailsState.value = ScreenState.FilmSuccess(response.body() ?: Movie())
//                } else {
//                    _filmDetailsState.value = ScreenState.FilmError("Failed to load movie details")
//                }
//            } catch (e: Exception) {
//                _filmDetailsState.value = ScreenState.FilmError("Error loading movie details")
//            }
//        }
//    }



    fun getFilmDetails(movieId: Int)  {
        // Здесь делаем запрос к API и обновляем состояние
        // Пример:
        viewModelScope.launch {
            _filmDetailsState.value = ScreenState.FilmLoading
            try {
                val movie = KinopoiskApi.retrofitService.getMovieDetails(movieId)
               // val movie = apiService.getMovieDetails(movieId)
                _filmDetailsState.value = ScreenState.FilmSuccess(movie)
            } catch (e: Exception) {
                _filmDetailsState.value = ScreenState.FilmError("Error loading movie details")
            }
        }

    }

    fun getActorDet(movieId: Int)  {
        // Здесь делаем запрос к API и обновляем состояние
        // Пример:
        viewModelScope.launch {
            _actorState.value = ScreenState.ActorLoading
            try {
                val actor = KinopoiskApi.retrofitService.getActor(movieId)
                // val movie = apiService.getMovieDetails(movieId)
                _actorState.value = ScreenState.ActorSuccess(actor)
            } catch (e: Exception) {
                _filmDetailsState.value = ScreenState.ActorError("Error loading movie details")
            }
        }

    }

}