package com.example.unsplash.api

import com.example.unsplash.dashboard.datamodel.UnsplashDataModel
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIService {
    @Headers("Content-Type: application/json")
    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ):UnsplashDataModel
}