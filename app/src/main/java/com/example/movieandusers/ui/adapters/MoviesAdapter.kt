package com.example.movieandusers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieandusers.databinding.ItemMovieBinding

class MoviesAdapter(private var movieList: List<ItemMovie>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    fun setMovies(data: List<ItemMovie>) {
        movieList = data
        notifyDataSetChanged()
    }
}

class MovieViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(itemMovie: ItemMovie) {
        with(binding) {
            labelName.text = itemMovie.name
            labelDescription.text = itemMovie.description
            imageView.load(itemMovie.image)
        }
    }
}