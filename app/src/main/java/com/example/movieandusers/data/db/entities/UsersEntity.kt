package com.example.movieandusers.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var phone: String = "",
    var email: String = "",
    var address: String = "",
    var image: String = "",
)