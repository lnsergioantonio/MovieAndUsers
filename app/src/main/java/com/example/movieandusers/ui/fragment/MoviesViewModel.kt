package com.example.movieandusers.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandusers.domain.movies.FetchMoviesUseCase
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.base.UseCase
import com.example.movieandusers.domain.entities.Movie

class MoviesViewModel(
    private val fetchMoviesUseCase: FetchMoviesUseCase,
) : ViewModel() {

    private val moviesLiveData = MutableLiveData<State<List<Movie>>>()
    val moviesState: LiveData<State<List<Movie>>> get() = moviesLiveData

    fun fetchMovies() {
        fetchMoviesUseCase.invoke(viewModelScope, UseCase.None()) {
            moviesLiveData.value = it
        }
    }

}