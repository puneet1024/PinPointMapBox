package com.example.puneet.gotennatest.models

/**
 * Class which provides a model for Data
 * @constructor sets all properties for the Json Data
 * @property id is the unique identifier
 * @property name is the name of the location
 * @property latitude is the latitude coordinate of the location
 * @property longitude is the longitude coordinate of the location
 * @property description description about the location
 */
data class Data(var id: Int, var name: String, var latitude: Float, var longitude: Float, var description: String)