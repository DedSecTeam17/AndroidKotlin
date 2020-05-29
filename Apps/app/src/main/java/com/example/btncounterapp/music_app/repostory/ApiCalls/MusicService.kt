package com.example.btncounterapp.music_app.repostory.ApiCalls

import com.example.btncounterapp.music_app.repostory.ApiCalls.Calls.AppApiCalls
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    const val baseUrl = "http://10.0.2.2:1337"
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("$baseUrl/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun musicServiceProvider(): AppApiCalls {
        return retrofit.create(AppApiCalls::class.java)
    }
}