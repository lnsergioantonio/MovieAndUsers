package com.example.movieandusers.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandusers.data.db.MovieAndUsersDB
import com.example.movieandusers.data.db.provideUsersDao
import com.example.movieandusers.databinding.FragmentUsersBinding
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.entities.User
import com.example.movieandusers.domain.users.FetchUsersUseCase
import com.example.movieandusers.domain.users.SaveUsersUseCase
import com.example.movieandusers.domain.users.UserRepositoryImpl
import com.example.movieandusers.ui.AddUserActivity
import com.example.movieandusers.ui.adapters.ItemUser
import com.example.movieandusers.ui.adapters.UserAdapter

class UsersFragment : Fragment() {
    private lateinit var binding: FragmentUsersBinding
    private lateinit var viewModel: UsersViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val db = MovieAndUsersDB.getDatabase(requireActivity().application)
        val dao = provideUsersDao(db)
        val repository = UserRepositoryImpl(dao)
        val userUseCase = FetchUsersUseCase(repository)
        val saveUserUseCase = SaveUsersUseCase(repository)
        viewModel = UsersViewModel(userUseCase, saveUserUseCase)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.fetchUsers()
    }

    private fun initObservers() {
        viewModel.usersState.observe(viewLifecycleOwner,this::onChangeUsersState)
    }

    private fun onChangeUsersState(result: State<List<User>>?) {
        result?.let { noNullResult ->
            when (noNullResult) {
                is State.Failure -> {
                    Log.e("Main", "onChangeUsersState", noNullResult.exception)
                }
                is State.Progress -> {}
                is State.Success -> {
                    adapter.setMovies(noNullResult.data.toItems())
                }
            }
        }
    }

    private fun initViews() {
        with(binding){
            adapter = UserAdapter(emptyList())
            recyclerView.adapter = adapter

            addNewUser.setOnClickListener {
                startActivity(Intent(requireContext(),AddUserActivity::class.java))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UsersFragment()
    }
}

fun List<User>.toItems(): List<ItemUser> {
    val result : MutableList<ItemUser> = mutableListOf()
    this.forEach {
        result.add(
            ItemUser(
                id = it.id,
                name = it.name,
                phone = it.phone,
                email = it.email,
                address = it.address,
                image = it.image
            )
        )
    }
    return result
}