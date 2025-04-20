package com.example.gamedrive.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RestClient {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var retrofit: Retrofit
    private lateinit var gameApi: GameApi

    private val loggingInterceptor = HttpLoggingInterceptor()

    fun init() {


        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        retrofit = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/LukaLominadze/GameDrive/api/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        gameApi = retrofit.create(GameApi::class.java)
    }

    fun getGameApi(): GameApi {
        if (!::gameApi.isInitialized) {
            throw IllegalStateException("RestClient not initialized. Call RestClient.init() first.")
        }
        return gameApi
    }
}