package com.example.movieandusers.domain.movies

import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.base.UseCase
import com.example.movieandusers.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class FetchMoviesUseCase(private val repository: MoviesRepository):
    UseCase<List<Movie>, UseCase.None>() {

    override fun run(params: None): Flow<State<List<Movie>>> {
        return repository.fetchMovies()
    }

}