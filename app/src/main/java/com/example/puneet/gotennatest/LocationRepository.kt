package com.example.puneet.gotennatest

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.puneet.gotennatest.R.id.recycler_view
import com.example.puneet.gotennatest.models.Data
import com.example.puneet.gotennatest.services.LocationService
import com.example.puneet.gotennatest.services.ServiceBuilder
import kotlinx.android.synthetic.main.layout_list.*

class LocationRepository: ViewModel() {
    private val TAG = LocationRepository::class.qualifiedName
    fun loadLocations():MutableLiveData<List<Data>> {
        val locationService = ServiceBuilder.buildService(LocationService::class.java)
        val requestCall = locationService.getLocationList()
        val locationList = MutableLiveData<List<Data>>()
        Log.d(TAG,"requestCall " + requestCall)
        requestCall.enqueue(object : retrofit2.Callback<List<Data>> {
            override fun onResponse(call: retrofit2.Call<List<Data>>?, response: retrofit2.Response<List<Data>>?) {
                if(response!!.isSuccessful) {
                    val result: List<Data> = (response.body() as ArrayList<Data>?)!!
                    locationList.value = result
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Data>>?, t: Throwable?) {
                Log.d(TAG,"error ")
            }
        })
        return locationList
    }
    companion object {

        private var locationRepository: LocationRepository? = null

        val instance: LocationRepository
            get() {
                if (locationRepository == null) {
                    locationRepository = LocationRepository()
                }
                return locationRepository!!
            }
    }
}