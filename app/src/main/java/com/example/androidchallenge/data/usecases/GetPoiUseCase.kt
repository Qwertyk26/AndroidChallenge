package com.example.androidchallenge.data.usecases

import com.example.androidchallenge.data.ServerApi
import com.example.androidchallenge.domain.models.poi.Poi
import retrofit2.Response
import javax.inject.Inject

interface GetPoiUseCase {
    suspend fun call(distance: Int, latitude: Double, longitude: Double): Response<List<Poi>>
}

class GetPoiUseCaseImpl @Inject constructor(private val api: ServerApi): GetPoiUseCase {
    override suspend fun call(distance: Int, latitude: Double, longitude: Double): Response<List<Poi>> {
        return api.getPoi(distance, latitude, longitude)
    }

}