package com.example.cozybite.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val getApi : MealApi by lazy {
         Retrofit.Builder()
             .baseUrl(BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(MealApi::class.java)
    }

}