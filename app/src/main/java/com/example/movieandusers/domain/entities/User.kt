package com.example.movieandusers.domain.entities

data class User(
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var address: String = "",
    var image: String = "",
)