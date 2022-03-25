package com.example.movieandusers.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.ui.AppBarConfiguration
import com.example.movieandusers.R
import com.example.movieandusers.data.db.MovieAndUsersDB
import com.example.movieandusers.data.db.provideUsersDao
import com.example.movieandusers.databinding.ActivityAddUserBinding
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.users.FetchUsersUseCase
import com.example.movieandusers.domain.users.SaveUsersUseCase
import com.example.movieandusers.domain.users.UserRepositoryImpl
import com.example.movieandusers.ext.isEmail
import com.example.movieandusers.ext.isEmpty
import com.example.movieandusers.ext.isPhone
import com.example.movieandusers.ui.fragment.UsersViewModel
import com.example.movieandusers.ui.fragment.toItems

class AddUserActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initViewModel()
        initObservers()
    }

    private fun initObservers() {
        viewModel.saveUserState.observe(this,this::onChangeSave)
    }

    private fun onChangeSave(result : State<Boolean>?){
        result?.let { noNullResult ->
            when (noNullResult) {
                is State.Failure -> {
                    Log.e("Main", "onChangeSave", noNullResult.exception)
                }
                is State.Progress -> {}
                is State.Success -> {
                    finish()
                }
            }
        }
    }

    private fun initViewModel() {
        val db = MovieAndUsersDB.getDatabase(application)
        val dao = provideUsersDao(db)
        val repository = UserRepositoryImpl(dao)
        val userUseCase = FetchUsersUseCase(repository)
        val saveUserUseCase = SaveUsersUseCase(repository)
        viewModel = UsersViewModel(userUseCase, saveUserUseCase)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.saveUser ->{
                saveData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveData() {
        var hasError = false
        with(binding){
            if (inputName.isEmpty()){
                hasError = true
                inputName.error = getString(R.string.field_invalid)
            }
            else
                inputName.error = null

            if (inputEmail.isEmpty() || !inputEmail.text.toString().isEmail()) {
                hasError = true
                inputEmail.error = getString(R.string.field_invalid)
            } else{
                inputEmail.error = null
            }

            if (inputPhone.isEmpty() || !inputPhone.text.toString().isPhone()){
                hasError = true
                inputPhone.error = getString(R.string.field_invalid)
            } else
                inputPhone.error = null

            if (inputAddress.isEmpty()){
                hasError = true
                inputAddress.error = getString(R.string.field_invalid)
            } else
                inputAddress.error = null

            if (!hasError){
                viewModel.saveUser(
                    name = inputName.text.toString(),
                    phone = inputPhone.text.toString(),
                    email = inputEmail.text.toString(),
                    address = inputAddress.text.toString(),
                )
            }
        }
    }
}