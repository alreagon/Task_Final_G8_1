package com.example.challengechapter8binar.dependency_injection

import com.example.challengechapter8binar.networking.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //base url for user API
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_URL_2 = "https://6254434c19bc53e2347b93f1.mockapi.io/"


    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    //retrofit and api services module for film
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    //retrofit and api services modeule for user
    @Provides
    @Singleton
    @Named("RetrofitUser")
    fun provideRetrofitForUserApi(): Retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL_2)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    @Named("ApiServicesUser")
    fun provideApiServicesForUser(@Named("RetrofitUser") retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)
}