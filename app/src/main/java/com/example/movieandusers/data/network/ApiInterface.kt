package com.example.movieandusers.data.network

import com.example.movieandusers.data.network.enties.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("4/discover/movie")
    suspend fun fetchMovies(
        @Query("sort_by") sortBy: String// popularity.desc
    ): Response<MoviesResponse>
}