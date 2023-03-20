package com.example.quiz_api

import com.example.quiz_api.model.MetadataQuiz
import com.example.quiz_api.model.Question
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object{

        val gson = GsonBuilder().setDateFormat("yy-MM-dd").create()

        val apiService = Retrofit.Builder()
            .baseUrl("https://the-trivia-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }

    @GET("metadata")
    fun getCategory() : Call<MetadataQuiz>

    @GET("questions")
    fun getRandomTypeQuestion() : Call<List<Question>>

    @GET("questions")
    fun getTypeQuestion(@Query("categories") type:String) : Call<List<Question>>


}