package com.example.unsplash.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    var httpClient = OkHttpClient.Builder()
    private val logInterceptor = HttpLoggingInterceptor()
    private const val BASE_URL = "https://picsum.photos/v2/"
    fun invoke(): APIService {
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) //adding interceptor to log the request-params and its response
        httpClient.addInterceptor(
            Interceptor { chain ->
                chain.proceed(chain.request())
            }).addInterceptor(logInterceptor)

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
        return retrofit.create(APIService::class.java)
    }
}