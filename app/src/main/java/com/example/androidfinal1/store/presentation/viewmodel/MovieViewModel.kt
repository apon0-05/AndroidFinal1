package com.example.androidfinal1.store.presentation.viewmodel

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
    data class ActorSuccess(val actor: List<Actor>): ScreenState
    data class ActorError(val message: String): ScreenState


    object ActorFilmsLoading: ScreenState
    data class ActorFilmsSuccess(val actor: ActorDetailsResponse,  val filmsByProfession: Map<String, List<ActorFilm>> ): ScreenState
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
            0 // Возвращаем 0, если состояние еще не успешно
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
            0 // Возвращаем 0, если состояние еще не успешно
        }
    }

    fun getActorDetails(actorId: Int) {
        viewModelScope.launch {
            _actorDetailsState.value = ScreenState.ActorInfoLoading // This is for actor details loading state
            try {
                // Fetch actor details
                val response = KinopoiskApi.retrofitService.getActorDetails(actorId)
                _actorDetailsState.value = ScreenState.ActorInfoSuccess(response) // Update with actor data

                // Fetch movie details only after actor info is loaded
                if (!response.films.isNullOrEmpty()) {
                    fetchFilmDetails(response.films.take(8)) // Limit to first 8 films
                } else {
                    _movies.value = emptyList() // No movies available
                }
            } catch (e: Exception) {
                // Handle error when fetching actor details
                _actorDetailsState.value = ScreenState.ActorInfoError("Error loading actor details")
            }
        }
    }

    private fun fetchFilmDetails(films: List<ActorFilm>?) {
        viewModelScope.launch {
            _movies.value = emptyList() // Set movies to empty list initially

            if (films.isNullOrEmpty()) return@launch

            try {
                // Fetch movie details
                val movieDetails = films.mapNotNull { actorFilm ->
                    try {
                        actorFilm.filmId?.let { movieId ->
                            // Получаем данные о фильме с постером
                            val movie = KinopoiskApi.retrofitService.getMovieDetails(movieId)
                            movie.posterUrl?.let {
                                // Добавляем постер фильма в результат
                                movie.copy(posterUrl = it)
                            }
                        }
                    } catch (e: Exception) {
                        null // Ignore errors for individual movie fetches
                    }
                }

                _movies.value = movieDetails // Set the movie details
            } catch (e: Exception) {
                _movies.value = emptyList() // In case of failure, set movies to empty list
            }
        }
    }



    fun processFilmsByProfession(actorResponse: ActorDetailsResponse): Map<String, List<ActorFilm>> {
        return actorResponse.films!!.map { film ->
            // Генерация URL для постера
            val posterUrl = generatePosterUrl(film.posterUrl)  // Предположим, что поле API называется posterPath
            film.copy(posterUrl = posterUrl)  // Обновляем объект ActorFilm
        }.groupBy { it.professionKey.toString() }
    }


    // Функция для генерации URL постера
    fun generatePosterUrl(posterPath: String?): String? {
        return if (posterPath != null) {
            // Генерируем полный URL для постера
            "https://kinopoiskapiunofficial.tech/images/posters/kp/${posterPath}"
        } else {
            null
        }
    }

    fun getActorDetailsFilmPo(actorId: Int) {
        viewModelScope.launch {
            _actorDetailsState.value = ScreenState.ActorFilmsLoading // Для загрузки
            try {
                // Запрос деталей актера
                val response = KinopoiskApi.retrofitService.getActorDetails(actorId)

                // Группируем фильмы по профессиям
                val filmsByProfession = processFilmsByProfession(response)

                // Обновляем состояние с деталями актера и группировкой фильмов
                _actorDetailsState.value = ScreenState.ActorFilmsSuccess(
                    actor = response,
                    filmsByProfession = filmsByProfession
                )
            } catch (e: Exception) {
                // Обработка ошибки
                _actorDetailsState.value = ScreenState.ActorFilmsError("Ошибка загрузки данных актера")
            }
        }
    }


    private fun fetchFilmsOfActor(films: List<ActorFilm>?) {
        viewModelScope.launch {
            _moviesactor.value = emptyList() // Сброс списка фильмов перед загрузкой

            if (films.isNullOrEmpty()) return@launch

            try {
                val updatedFilms = films.mapNotNull { actorFilm ->
                    try {
                        actorFilm.filmId?.let { movieId ->
                            val movie = KinopoiskApi.retrofitService.getMovieDetails(movieId)
                            actorFilm.posterUrl = movie.posterUrl // Устанавливаем URL постера
                            actorFilm // Возвращаем объект ActorFilm с обновленным постером
                        }
                    } catch (e: Exception) {
                        null
                    }
                }

                _moviesactor.value = updatedFilms // Обновляем состояние с типом ActorFilm
            } catch (e: Exception) {
                _moviesactor.value = emptyList()
            }
        }
    }



    fun fetchActorFilms(actorId: Int) {
        viewModelScope.launch {
            try {
                val personDetails = KinopoiskApi.retrofitService.getActorDetails(actorId)
                val filmsWithPosters = personDetails.films?.map { film ->
                    val filmDetails = KinopoiskApi.retrofitService.getMovieDetails(film.filmId)
                    film.copy(
                        posterUrl = filmDetails.posterUrl,
                        nameRu = filmDetails.nameRu,
                    )
                } ?: emptyList()

                _actorFilms.value = filmsWithPosters.groupBy { it.professionKey ?: "Unknown" }
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch actor films: ${e.message}"
            }
        }
    }





}