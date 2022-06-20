package com.example.challengechapter8binar.networking

import com.example.challengechapter8binar.model.GetAllUserResponseItem
import com.example.challengechapter8binar.model.MovieResponse
import com.example.challengechapter8binar.model.PostUser
import com.example.challengechapter8binar.model.RequestUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String
    ): Call<MovieResponse>

    //user
    @GET("datauserlogin")
    suspend fun getAllUser(): List<GetAllUserResponseItem>

    @POST("datauserlogin")
    fun addNewUser(@Body requestUser: RequestUser): Call<PostUser>
}