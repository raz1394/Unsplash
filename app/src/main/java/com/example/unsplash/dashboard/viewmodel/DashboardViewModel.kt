package com.example.unsplash.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplash.api.Resource
import com.example.unsplash.dashboard.datamodel.UnsplashDataItemsModel
import com.example.unsplash.dashboard.datamodel.UnsplashDataModel
import com.example.unsplash.dashboard.repository.DashboardRepository
import kotlinx.coroutines.launch

class DashboardViewModel constructor(private val dashboardRepository: DashboardRepository) :ViewModel() {
    var rowsArrayList=ArrayList<UnsplashDataItemsModel>()
    private var count: Int = 0
    private var pages: Int = 1
    private val _getImagesResponse: MutableLiveData<Resource<UnsplashDataModel>> = MutableLiveData()

    val getImagesResponse: LiveData<Resource<UnsplashDataModel>>
        get() = _getImagesResponse

    init {
        setCount(0)
        getImages(1,10)
    }

    fun clear(){
        count=0
        pages=1
    }

    fun addItems(data:ArrayList<UnsplashDataItemsModel>){
        rowsArrayList.addAll(data)
    }

    fun getItems() : ArrayList<UnsplashDataItemsModel>{
        return rowsArrayList
    }

    fun setCount(countUpdate:Int){
        count += countUpdate
    }

    fun setPages(pagesUpdate:Int){
        pages += pagesUpdate
    }

    fun getCount():Int{
        return count
    }

    fun getPages():Int{
        return pages
    }

    fun getImages(page: Int, limit: Int) = viewModelScope.launch {
        _getImagesResponse.value = Resource.Loading
        _getImagesResponse.value = dashboardRepository.getImages(page, limit)
    }
}