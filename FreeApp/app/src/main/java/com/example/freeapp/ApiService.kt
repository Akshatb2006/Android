package com.example.freeapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    fun getTodos(): Call<List<chat>>
}
