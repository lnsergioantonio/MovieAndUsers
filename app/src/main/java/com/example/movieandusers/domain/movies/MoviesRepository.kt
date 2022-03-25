package com.example.movieandusers.domain.movies

import com.example.movieandusers.data.db.dao.MoviesDao
import com.example.movieandusers.data.network.ApiInterface
import com.example.movieandusers.data.network.NetworkHandler
import com.example.movieandusers.domain.base.NoDataException
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

interface MoviesRepository {
    fun fetchMovies(): Flow<State<List<Movie>>>
}

class MoviesRepositoryImpl(
    private val api: ApiInterface,
    private val moviesDao: MoviesDao,
    private val networkHandler: NetworkHandler
) : MoviesRepository {

    override fun fetchMovies(): Flow<State<List<Movie>>> {
        return flow {
            val result = when (networkHandler.isConnected) {
                true -> {
                    api.fetchMovies("popularity.desc").run {
                        if (isSuccessful && body() != null) {
                            moviesDao.deleteAllAndInsert(body()!!.results.toEntityList())
                            State.Success(findLastMovies())
                        } else {
                            State.Failure(NoDataException())
                        }
                    }
                }
                false -> {
                    State.Success(findLastMovies())
                }
            }
            emit(result)
        }.onStart {
            State.Progress(true)
        }.catch {
            State.Failure(it)
        }
    }

    private suspend fun findLastMovies(): List<Movie> {
        return moviesDao.findAll().toDomain()
    }
}

