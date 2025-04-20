package com.example.gamedrive.api

import retrofit2.Response
import retrofit2.http.GET

interface GameApi {
    @GET("games.json")
    suspend fun getGames(): Response<List<Game>>

    @GET("categories.json")
    suspend fun getCategories(): Response<List<Category>>
}