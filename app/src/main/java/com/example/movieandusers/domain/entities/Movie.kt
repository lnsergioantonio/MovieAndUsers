package com.example.movieandusers.domain.entities

data class Movie(
    var id: Int,
    var backdropUri: String = "",
    var originalLanguage: String = "",
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double? = null,
    var posterUri: String = "",
    var releaseDate: String = "",
    var title: String = "",
    var voteAverage: Double = 0.0,
    var voteCount: Int = 0
)
