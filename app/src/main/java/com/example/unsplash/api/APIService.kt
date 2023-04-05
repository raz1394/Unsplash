package com.example.unsplash.api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?,
    ):JsonObject
}