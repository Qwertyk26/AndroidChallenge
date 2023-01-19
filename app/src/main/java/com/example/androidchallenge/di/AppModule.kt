package com.example.androidchallenge.di

import com.example.androidchallenge.BuildConfig
import com.example.androidchallenge.data.ServerApi
import com.example.androidchallenge.data.usecases.GetPoiUseCase
import com.example.androidchallenge.data.usecases.GetPoiUseCaseImpl
import com.example.androidchallenge.di.converter.AddressInfoConverter
import com.example.androidchallenge.domain.models.poi.AddressInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideServerApi(retrofit: Retrofit): ServerApi {
        return retrofit.create(ServerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(AddressInfo::class.java, AddressInfoConverter()) // Here we need a converter, cause GsonConverterFactory can't understand the specific German symbols from responses like 'ß'
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(RetrofitInterceptor())
            .connectTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private class RetrofitInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .addQueryParameter("camelcase", "true")
                .addQueryParameter("compact", "true")
                .build()

            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)

            val request: Request = requestBuilder.build()
            return chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun providePoiUseCae(useCase: GetPoiUseCaseImpl): GetPoiUseCase = useCase

    @Provides
    @Singleton
    internal fun providesDispatchersProvider(dispatchersProvider: DispatchersProviderImpl): DispatchersProvider = dispatchersProvider
}