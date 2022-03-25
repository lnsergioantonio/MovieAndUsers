package com.example.movieandusers.domain.users

import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.base.UseCase
import com.example.movieandusers.domain.entities.User
import kotlinx.coroutines.flow.Flow

class FetchUsersUseCase(private val repository: UserRepository):
    UseCase<List<User>, UseCase.None>() {

    override fun run(params: None): Flow<State<List<User>>> {
        return repository.fetchUsers()
    }

}