package com.example.unsplash.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unsplash.dashboard.repository.DashboardRepository

class DashboardViewModelFactory constructor(private val dashboardRepository: DashboardRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            DashboardViewModel(this.dashboardRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}