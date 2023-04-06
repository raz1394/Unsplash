package com.example.unsplash.dashboard.repository

import com.example.unsplash.api.APIService
import com.selrgroup.selrqr.api.SafeApiCall

class DashboardRepository(private val apiService: APIService) :
    SafeApiCall {

    suspend fun getImages(page: Int, limit: Int) = safeApiCall {
        apiService.getImages(page,limit)
    }

}
