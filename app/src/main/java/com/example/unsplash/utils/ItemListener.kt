package com.example.unsplash.utils

import com.example.unsplash.dashboard.datamodel.UnsplashDataItemsModel


interface ItemListener {
    fun onItemClick(position: Int, data: ArrayList<UnsplashDataItemsModel>? = null, action:String="")
}
