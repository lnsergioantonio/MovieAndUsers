package com.example.movieandusers.domain.movies

import com.example.movieandusers.BuildConfig
import com.example.movieandusers.data.db.entities.MoviesEntity
import com.example.movieandusers.data.network.enties.MovieResponse
import com.example.movieandusers.domain.entities.Movie

fun List<MovieResponse>?.toEntityList(): List<MoviesEntity> {
    val result: MutableList<MoviesEntity> = arrayListOf()
    this?.forEach {
        result.add(
            MoviesEntity(
                id = it.id ?: 0,
                backdropPath = it.backdropPath.orEmpty(),
                originalLanguage = it.originalLanguage.orEmpty(),
                originalTitle = it.originalTitle.orEmpty(),
                overview = it.overview.orEmpty(),
                popularity = it.popularity ?: 0.0,
                posterPath = it.posterPath.orEmpty(),
                releaseDate = it.releaseDate.orEmpty(),
                title = it.title.orEmpty(),
                voteAverage = it.voteAverage ?: 0.0,
                voteCount = it.voteCount ?: 0
            )
        )
    }
    return result
}

fun List<MoviesEntity>?.toDomain(): List<Movie> {
        val result: MutableList<Movie> = arrayListOf()
        this?.forEach {
            result.add(
                Movie(
                    id = it.id,
                    backdropUri = it.backdropPath,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    overview = it.overview,
                    popularity = it.popularity ?: 0.0,
                    posterUri = "${BuildConfig.URL_BASE_PIC}${it.posterPath}",
                    releaseDate = it.releaseDate,
                    title = it.title,
                    voteAverage = it.voteAverage,
                    voteCount = it.voteCount
                )
            )
        }
        return result
    }
