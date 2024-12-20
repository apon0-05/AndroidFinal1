package com.example.androidfinal1.store.presentation.screen.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Filters(
    val country: String? = null,
    val genre: String? = null,
    val startYear: Int? = null,
    val endYear: Int? = null,
    val ratingRange: Pair<Float, Float>? = null,
    val sortBy: String = "Дата",
    val showType: String = "Все"
)
class FilterViewModel : ViewModel() {

    private val _filters = MutableStateFlow(Filters())
    val filters: StateFlow<Filters> = _filters

    fun updateCountry(country: String) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(country = country))
            Log.d("FilterViewModel", "Country Clicked: ${country}")
        }
    }

    fun updateGenre(genre: String) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(genre = genre))
        }
    }

    fun updateStartYear(startYear: Int) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(startYear = startYear))
        }
    }

    fun updateEndYear(endYear: Int) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(endYear = endYear))
        }
    }


    fun updateRatingRange(ratingRange: Pair<Float, Float>) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(ratingRange = ratingRange))
        }
    }

    fun updateSortBy(sortBy: String) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(sortBy = sortBy))
        }
    }

    fun updateShowType(showType: String) {
        viewModelScope.launch {
            _filters.emit(_filters.value.copy(showType = showType))
        }
    }

    fun resetFilters() {
        viewModelScope.launch {
            _filters.emit(Filters())
        }
    }
}
