package com.example.movieandusers.domain.users

import com.example.movieandusers.data.db.entities.UsersEntity
import com.example.movieandusers.domain.entities.User

fun List<UsersEntity>?.toDomain(): List<User> {
    val result: MutableList<User> = arrayListOf()
    this?.forEach {
        result.add(
            User(
                id = it.id,
                name = it.name,
                phone = it.phone,
                email = it.email,
                address = it.address,
                image = it.image,
            )
        )
    }
    return result
}

