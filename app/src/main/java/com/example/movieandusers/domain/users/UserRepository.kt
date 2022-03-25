package com.example.movieandusers.domain.users

import com.example.movieandusers.data.db.dao.UsersDao
import com.example.movieandusers.data.db.entities.UsersEntity
import com.example.movieandusers.domain.entities.User
import com.example.movieandusers.domain.base.State
import kotlinx.coroutines.flow.*

interface UserRepository {
    fun fetchUsers(): Flow<State<List<User>>>
    fun saveUser(
        name: String,
        phone: String,
        email: String,
        address: String,
        image: String
    ): Flow<State<Boolean>>
}

class UserRepositoryImpl(
    private val usersDao: UsersDao
) : UserRepository {
    override fun fetchUsers(): Flow<State<List<User>>> {
        return usersDao.findAllByFlow().map {
            State.Success(it.toDomain())
        }
    }

    override fun saveUser(
        name: String,
        phone: String,
        email: String,
        address: String,
        image: String
    ): Flow<State<Boolean>> {
        return flow {
            val user = UsersEntity(
                name = name,
                phone = phone,
                email = email,
                address = address,
                image = ""
            )
            usersDao.insert(user)
            emit(State.Success(true))
        }.onStart {
            State.Progress(true)
        }.catch {
            State.Failure(it)
        }
    }
}