package com.marxelosj.kotlinflowsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marxelosj.kotlinflowsample.data.domain.Movie
import com.marxelosj.kotlinflowsample.data.domain.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MoviesRepository) : ViewModel() {

    private val _spinner = MutableStateFlow(true)
    val spinner: StateFlow<Boolean> get() = _spinner

    val movies: Flow<List<Movie>> get() = repository.getMovies()

    init {
        viewModelScope.launch { notifyLastVisible(0) }
    }

    suspend fun notifyLastVisible(lastVisible: Int) {
        repository.checkRequireNewPage(lastVisible)
        _spinner.value = false
    }
}