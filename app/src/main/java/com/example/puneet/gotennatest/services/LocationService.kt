package com.example.puneet.gotennatest.services

import com.example.puneet.gotennatest.models.Data
import retrofit2.Call
import retrofit2.http.GET

interface LocationService {

    @GET("get_map_pins.php")
    fun getLocationList() : Call<List<Data>>
}