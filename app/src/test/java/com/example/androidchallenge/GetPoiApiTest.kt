package com.example.androidchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.androidchallenge.data.ServerApi
import com.example.androidchallenge.data.usecases.GetPoiUseCaseImpl
import com.example.androidchallenge.domain.models.poi.Poi
import com.example.androidchallenge.util.MockResponseFileReader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.bouncycastle.asn1.x500.style.RFC4519Style.description
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class GetPoiApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var getPoiUseCase: GetPoiUseCaseImpl
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {
        server.start(8000)
        val BASE_URL = server.url("/").toString()
        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(ServerApi::class.java)

        getPoiUseCase = GetPoiUseCaseImpl(service)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("poi.json").content
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )
        val response = runBlocking { getPoiUseCase.call(5, 52.526, 13.415) }
        val json = gson.toJson(response.body())
        val resultResponse = Gson().fromJson(json, Array<Poi>::class.java)
        val expectedResponse = Gson().fromJson(mockedResponse, Array<Poi>::class.java)
        Assert.assertNotNull(response)
        Assert.assertEquals(resultResponse, expectedResponse)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}