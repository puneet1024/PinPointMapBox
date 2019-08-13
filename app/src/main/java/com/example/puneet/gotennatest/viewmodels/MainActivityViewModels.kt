package com.example.puneet.gotennatest.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.puneet.gotennatest.LocationRepository

import com.example.puneet.gotennatest.models.Data

class MainActivityViewModels: ViewModel() {
    private var mutableLiveData: MutableLiveData<List<Data>>? = null
    private var locationRepository: LocationRepository? = null

    fun init() {
        if (mutableLiveData != null) {
            return
        }
        locationRepository = LocationRepository()
        mutableLiveData = locationRepository!!.loadLocations()

    }
    fun getLocationRepository(): LiveData<List<Data>>? {
        return mutableLiveData
    }
}
