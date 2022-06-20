package com.example.challengechapter8binar.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.challengechapter8binar.model.Movie
import com.example.challengechapter8binar.model.MovieResponse
import com.example.challengechapter8binar.networking.ApiServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(apiServices: ApiServices) : ViewModel() {
    private val movieState = MutableStateFlow(emptyList<Movie>())
    val dataMovieState: StateFlow<List<Movie>> get() = movieState
    private val api = apiServices
    private val apiKey = "38c63d3167f5d3b3dc87620291bc2b1d"

    init {
        val command = api.getMovie(apiKey)
        command.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    movieState.value = response.body()!!.results
                } else {
                    Log.e("view_model", response.message())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("view_model_error", t.message.toString())
            }

        })
    }
}