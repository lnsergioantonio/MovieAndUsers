package com.example.movieandusers.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandusers.BuildConfig
import com.example.movieandusers.data.db.MovieAndUsersDB
import com.example.movieandusers.data.db.provideMoviesDao
import com.example.movieandusers.data.network.ApiInterface
import com.example.movieandusers.data.network.Network
import com.example.movieandusers.data.network.NetworkHandler
import com.example.movieandusers.databinding.FragmentMoviesBinding
import com.example.movieandusers.domain.movies.FetchMoviesUseCase
import com.example.movieandusers.domain.base.State
import com.example.movieandusers.domain.entities.Movie
import com.example.movieandusers.domain.movies.MoviesRepositoryImpl
import com.example.movieandusers.ui.adapters.ItemMovie
import com.example.movieandusers.ui.adapters.MoviesAdapter

class MoviesFragment : Fragment() {
    private lateinit var viewModel: MoviesViewModel
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewModel.fetchMovies()
    }

    private fun initViews() {
        adapter = MoviesAdapter(emptyList())
        binding.recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        val db = MovieAndUsersDB.getDatabase(requireActivity().application)
        val dao = provideMoviesDao(db)
        val api =
            Network.createNetworkClient(true, BuildConfig.URL_BASE).create(ApiInterface::class.java)
        val repository = MoviesRepositoryImpl(api, dao, NetworkHandler(requireContext()))
        val movieUseCase = FetchMoviesUseCase(repository)
        viewModel = MoviesViewModel(movieUseCase)
    }

    private fun initObservers() {
        viewModel.moviesState.observe(viewLifecycleOwner, this::onChangeMovieState)
    }

    private fun onChangeMovieState(result: State<List<Movie>>?) {
        result?.let { noNullResult ->
            when (noNullResult) {
                is State.Failure -> {
                    Log.e("Main", "onChangeMovieState", noNullResult.exception)
                }
                is State.Progress -> {}
                is State.Success -> {
                    adapter.setMovies(noNullResult.data.toItems())
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MoviesFragment()
    }
}

fun List<Movie>.toItems(): List<ItemMovie> {
    val result : MutableList<ItemMovie> = mutableListOf()
    this.forEach {
        result.add(
            ItemMovie(
                name = it.originalTitle,
                description = it.overview,
                image = it.posterUri
            )
        )
    }
    return result
}