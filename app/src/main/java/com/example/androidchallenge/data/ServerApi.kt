package com.example.androidchallenge.data

import com.example.androidchallenge.domain.models.poi.Poi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServerApi {

    @GET("poi")
    suspend fun getPoi(@Query("distance") distance: Int, @Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Response<List<Poi>>
}