package com.example.movieandusers.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesEntity(
    @PrimaryKey
    var id: Int,
    var backdropPath: String = "",
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double? = null,
    var posterPath: String = "",
    var releaseDate: String = "",
    var title: String = "",
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0
)