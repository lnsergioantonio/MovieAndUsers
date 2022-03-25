package com.example.movieandusers.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieandusers.domain.users.FetchUsersUseCase
import com.example.movieandusers.domain.users.SaveUsersUseCase
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.base.UseCase
import com.example.movieandusers.domain.entities.User

class UsersViewModel(
    private val userUseCase: FetchUsersUseCase,
    private val saveUserUseCase: SaveUsersUseCase) :ViewModel(){
    private val userLiveData = MutableLiveData<State<List<User>>>()
    val usersState : LiveData<State<List<User>>> get() = userLiveData

    private val saveUserLiveData = MutableLiveData<State<Boolean>>()
    val saveUserState : LiveData<State<Boolean>> get() = saveUserLiveData

    fun fetchUsers() {
        userUseCase.invoke(viewModelScope, UseCase.None()){
            userLiveData.value = it
        }
    }

    fun saveUser(name: String,
                 phone: String,
                 email: String,
                 address: String,
                 image: String = "") {
        val params = SaveUsersUseCase.Params(name, phone, email, address, image)
        saveUserUseCase.invoke(viewModelScope,params){
            saveUserLiveData.value = it
        }
    }
}
