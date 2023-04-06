package com.example.unsplash

import android.app.Application
import android.content.Context
import com.example.unsplash.api.RetrofitHelper
import com.example.unsplash.dashboard.repository.DashboardRepository
import kotlinx.coroutines.InternalCoroutinesApi

class MainApplication : Application() {

    lateinit var dashboardRepository:DashboardRepository


    private var mContext: Context? = null

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        initialize()
        getContext()
    }

    private fun getContext(): Context? {
        return mContext
    }


    @InternalCoroutinesApi
    private fun initialize() {
        val apiService = RetrofitHelper.invoke()
        dashboardRepository = DashboardRepository(apiService)


    }

}