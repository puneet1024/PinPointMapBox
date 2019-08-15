package com.example.puneet.gotennatest.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Class which provides a model for Data
 * @constructor sets all properties for the Json Data
 * @property id is the unique identifier
 * @property name is the name of the location
 * @property latitude is the latitude coordinate of the location
 * @property longitude is the longitude coordinate of the location
 * @property description description about the location
 */
@Entity
data class Data(@PrimaryKey(autoGenerate = true) var id: Int, var name: String, var latitude: Float, var longitude: Float, var description: String) {
        constructor() : this(0, "", 0.0F, 0.0F, "")
}