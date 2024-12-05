package com.example.androidfinal1.store.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfinal1.store.data.remote.Actor
import com.example.androidfinal1.store.data.remote.ActorDetailsResponse
import com.example.androidfinal1.store.data.remote.ActorFilm
import com.example.androidfinal1.store.data.remote.ImageItem
import com.example.androidfinal1.store.data.remote.KinopoiskApi
import com.example.androidfinal1.store.data.remote.Movie
import com.example.androidfinal1.store.data.remote.MovieId
import com.example.androidfinal1.store.data.remote.SimilarFilmItem
import com.example.androidfinal1.store.data.remote.toMovieId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

sealed interface ScreenState {
    object Initial : ScreenState
    object Loading : ScreenState
    data class Success(val movies: List<Movie>) : ScreenState
    data class Error(val message: String) : ScreenState

    object FilmLoading : ScreenState
    data class FilmSuccess(val movie: MovieId) : ScreenState
    data class FilmError(val message: String) : ScreenState

    data class SuccessMovieId(val movies: List<MovieId>) : ScreenState
   // data class SuccessMovieId(val movies: List<MovieId>) : ScreenState


    object ActorLoading: ScreenState
    data class ActorSuccess(val actor: List<Actor>): ScreenState
    data class ActorError(val message: String): ScreenState


    object ActorFilmsLoading: ScreenState
    data class ActorFilmsSuccess(val actor: ActorDetailsResponse, val filmsByProfession: Map<String?, List<ActorFilm>>): ScreenState
    data class ActorFilmsError(val message: String): ScreenState


    object ImageLoading : ScreenState
    data class ImageSuccess(val images: List<ImageItem>) : ScreenState
    data class ImageError(val message: String) : ScreenState

    object SimilarFilmsLoading : ScreenState
    data class SimilarFilmsSuccess(val films: List<SimilarFilmItem>) : ScreenState
    data class SimilarFilmsError(val message: String) : ScreenState

    object ActorInfoLoading: ScreenState
    data class ActorInfoSuccess(val actor: ActorDetailsResponse): ScreenState
    data class ActorInfoError(val message: String): ScreenState

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

    private val _actorDetailsState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val actorDetailsState: StateFlow<ScreenState> get() = _actorDetailsState

    private val _staffCount = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val staffCount: StateFlow<ScreenState> get() = _staffCount

    private val _imagesState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val imagesState: StateFlow<ScreenState> get() = _imagesState


    private val _similarFilmsState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val similarFilmsState: StateFlow<ScreenState> get() = _similarFilmsState

    private val _actorFilmsState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val actorFilmsState: StateFlow<ScreenState> = _actorFilmsState

    private val _movies = MutableStateFlow<List<MovieId>>(emptyList())
    val movies: StateFlow<List<MovieId>> get() = _movies

    private val _actorprofessionKey = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val actorprofessionKey: StateFlow<ScreenState> get() = _actorprofessionKey

    private val _moviesactor = MutableStateFlow<List<ActorFilm>>(emptyList())
    val moviesactor: StateFlow<List<ActorFilm>> = _moviesactor


    private val _actorFilms = MutableStateFlow<Map<String, List<ActorFilm>>>(emptyMap())
    val actorFilms: StateFlow<Map<String, List<ActorFilm>>> get() = _actorFilms

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _searchFilmsState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val searchFilmsState: StateFlow<ScreenState> get() = _searchFilmsState

    private val _films = MutableStateFlow<List<MovieId>>(emptyList())
    val films: StateFlow<List<MovieId>> get() = _films

    private val searchQueryFlow = MutableSharedFlow<String>(replay = 1)  // SharedFlow для запроса

    private val _moviesState = MutableStateFlow<ScreenState>(ScreenState.Initial)
    val moviesState: StateFlow<ScreenState> get() = _moviesState

    private val _filters = MutableStateFlow<FilterOptions>(FilterOptions()) // Стейт для фильтров
    val filters: StateFlow<FilterOptions> get() = _filters

    // Запрос фильмов с учетом фильтров
    fun searchFilteredFilms(
        countries: List<Int>?,
        genres: List<Int>?,
        yearFrom: Int,
        yearTo: Int,
        ratingFrom: Float,
        ratingTo: Float,
        order: String,
        type: String,
        page: Int
    ) {
        _moviesState.value = ScreenState.Loading  // Устанавливаем состояние загрузки

        viewModelScope.launch {
            try {
                val response = KinopoiskApi.retrofitService.searchFilms(
                    countries = countries?.joinToString(","),
                    genres = genres?.joinToString(","),
                    yearFrom = yearFrom,
                    yearTo = yearTo,
                    ratingFrom = ratingFrom,
                    ratingTo = ratingTo,
                    order = order,
                    type = type,
                    page = page
                )
                if (response.items.isNotEmpty()) {
                    //_moviesState.value = ScreenState.SuccessMovieId(response.items.map { it.toMovieId() })
                } else {
                    _moviesState.value = ScreenState.Error("Фильмов не найдено по данному запросу.")
                }
            } catch (e: Exception) {
                _moviesState.value = ScreenState.Error(e.message ?: "Ошибка загрузки данных")
            }
        }
    }

    // Функция для обновления фильтров
    fun updateFilters(filters: FilterOptions) {
        _filters.value = filters
    }


    init {
        viewModelScope.launch {
            searchQueryFlow
                .debounce(500)  // 500ms debounce
                .collect { query ->
                    searchFilms(query)
                }
        }
    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            searchQueryFlow.emit(query)
        }
    }

    fun searchFilms(keyword: String) {
        if (keyword.isEmpty()) {
            _searchFilmsState.value = ScreenState.Initial
            return
        }

        _searchFilmsState.value = ScreenState.Loading
        viewModelScope.launch {
            try {
                val response = KinopoiskApi.retrofitService.searchFilms(keyword)
                if (response.films.isNotEmpty()) {
                    val movies = response.films.map { it.toMovieId() }
                    _films.value = movies
                    _searchFilmsState.value = ScreenState.SuccessMovieId(movies)
                } else {
                    _searchFilmsState.value = ScreenState.Error("Нет фильмов по запросу: $keyword")
                }
            } catch (e: Exception) {
                _searchFilmsState.value = ScreenState.Error(e.message ?: "Ошибка загрузки данных")
            }
        }
    }





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
                _popularMoviesState.value =
                    ScreenState.Success(response.body()?.movies ?: emptyList())
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
                _zombieMoviesState.value =
                    ScreenState.Success(response.body()?.movies ?: emptyList())
            }
            else{
                _zombieMoviesState.value = ScreenState.Error("Failed to fetch premieres")
            }
        } catch (e: Exception) {
            _zombieMoviesState.value = ScreenState.Error("Network error")
        }

    }



    fun getFilmDetails(movieId: Int)  {
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
    fun getStaffCount(movieId: Int) {
        viewModelScope.launch {
            _staffCount.value = ScreenState.ActorLoading
            try {
                val staffList = KinopoiskApi.retrofitService.getStaffByFilmId(movieId)
                _staffCount.value = ScreenState.ActorSuccess(staffList) // Возвращаем список актеров
            } catch (e: Exception) {
                _staffCount.value = ScreenState.ActorError("Error loading staff details: ${e.message}")
            }
        }
    }

    fun getFilmImages(filmId: Int) {
        viewModelScope.launch {
            _imagesState.value = ScreenState.ImageLoading
            try {
                val response = KinopoiskApi.retrofitService.getFilmImages(filmId)
                _imagesState.value = ScreenState.ImageSuccess(response.items)
            } catch (e: Exception) {
                _imagesState.value = ScreenState.ImageError("Error loading images")
            }
        }
    }
    fun getFilmImagesCount(): Int {
        val currentState = _imagesState.value
        return if (currentState is ScreenState.ImageSuccess) {
            currentState.images.size
        } else {
            0
        }
    }

    fun getSimilarFilms(filmId: Int) {
        viewModelScope.launch {
            _similarFilmsState.value = ScreenState.SimilarFilmsLoading
            try {
                val response = KinopoiskApi.retrofitService.getSimilarFilms(filmId)
                _similarFilmsState.value = ScreenState.SimilarFilmsSuccess(response.items)
            } catch (e: Exception) {
                _similarFilmsState.value = ScreenState.SimilarFilmsError("Error loading similar films")
            }
        }
    }

    fun getSimilarFilmsCount(): Int {
        val currentState = _similarFilmsState.value
        return if (currentState is ScreenState.SimilarFilmsSuccess) {
            currentState.films.size
        } else {
            0
        }
    }

    fun getActorDetails(actorId: Int) {
        viewModelScope.launch {
            _actorDetailsState.value = ScreenState.ActorInfoLoading
            try {
                val response = KinopoiskApi.retrofitService.getActorDetails(actorId)
                _actorDetailsState.value = ScreenState.ActorInfoSuccess(response)

                if (!response.films.isNullOrEmpty()) {
                    fetchFilmDetails(response.films.take(8))
                } else {
                    _movies.value = emptyList()
                }
            } catch (e: Exception) {
                _actorDetailsState.value = ScreenState.ActorInfoError("Error loading actor details")
            }
        }
    }

    fun generatePosterUrl(posterPath: String?): String? {
        return if (posterPath != null) {
            "https://kinopoiskapiunofficial.tech/images/posters/kp/${posterPath}"
        } else {
            null
        }
    }


    private fun fetchFilmDetails(films: List<ActorFilm>?) {
        viewModelScope.launch {
            try {
                Log.d("fetchFilmDetails", "Fetching details for films: ${films?.map { it.filmId }}")
                val movieDetails = films?.mapNotNull { film ->
                    try {
                        KinopoiskApi.retrofitService.getMovieDetails(film.filmId) // Ваш метод
                    } catch (e: Exception) {
                        Log.e("fetchFilmDetails", "Error fetching film: ${film.filmId}", e)
                        null
                    }
                }
                if (movieDetails != null) {
                    _movies.value = movieDetails
                }
                Log.d("fetchFilmDetails", "Movies fetched: ${_movies.value.size}")
            } catch (e: Exception) {
                Log.e("fetchFilmDetails", "Error fetching films", e)
            }
        }
    }

    fun getActorDetailsFilmPo(actorId: Int) {
        viewModelScope.launch {
            _actorDetailsState.value = ScreenState.ActorFilmsLoading
            try {
                // Загружаем данные об актере
                val actorDetails = KinopoiskApi.retrofitService.getActorDetails(actorId)

                // Группируем фильмы по профессии
                val filmsByProfession = actorDetails.films?.groupBy { it.professionKey } ?: emptyMap()

                // Загрузка подробной информации о фильмах для каждой профессии
                val enrichedFilmsByProfession = filmsByProfession.mapValues { (_, films) ->
                    films.map { film ->
                        val details = fetchMovieDetails(film.filmId) // Загрузка всех данных фильма
                        film.copy(
                            posterUrl = details?.posterUrl,
                            nameRu = details?.nameRu ?: film.nameRu,
                            nameEn = details?.title ?: film.nameEn,
                            description = details?.description,
                            rating = details?.rating?.toString(),
                            professionKey = film.professionKey
                        ).apply {
                            year = details?.year
                            genres = details?.genres?.joinToString(", ") { it.name }?: ""
                        }
                    }
                }

                _actorDetailsState.value = ScreenState.ActorFilmsSuccess(
                    actor = actorDetails,
                    filmsByProfession = enrichedFilmsByProfession
                )
            } catch (e: Exception) {
                _actorDetailsState.value = ScreenState.ActorFilmsError("Ошибка загрузки данных актера")
            }
        }
    }

    private suspend fun fetchMovieDetails(filmId: Int): MovieId? {
        return try {
            KinopoiskApi.retrofitService.getMovieDetails(filmId)
        } catch (e: Exception) {
            Log.e("fetchMovieDetails", "Ошибка загрузки данных фильма $filmId", e)
            null
        }
    }


}

// Класс для хранения фильтров
data class FilterOptions(
    val countries: List<Int> = emptyList(),
    val genres: List<Int> = emptyList(),
    val yearFrom: Int = 1000,
    val yearTo: Int = 3000,
    val ratingFrom: Float = 0f,
    val ratingTo: Float = 10f,
    val order: String = "RATING",
    val type: String = "ALL"
)