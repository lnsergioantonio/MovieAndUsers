package com.example.movieandusers.domain.users

import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.base.UseCase
import com.example.movieandusers.domain.entities.User
import kotlinx.coroutines.flow.Flow

class SaveUsersUseCase(private val repository: UserRepository):
    UseCase<Boolean, SaveUsersUseCase.Params>() {

    override fun run(params: Params): Flow<State<Boolean>> {
        return repository.saveUser(
            params.name,
            params.phone,
            params.email,
            params.address,
            params.image
        )
    }

    data class Params(
        val name: String,
        val phone: String,
        val email: String,
        val address: String,
        val image: String
    )
}