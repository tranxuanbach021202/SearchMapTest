package com.example.searchmap.data.models

import com.google.gson.annotations.SerializedName

data class PlaceModel(
    val placeId: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
    val type: String
)