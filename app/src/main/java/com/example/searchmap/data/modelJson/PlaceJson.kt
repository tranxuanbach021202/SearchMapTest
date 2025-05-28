package com.example.searchmap.data.modelJson

import com.example.searchmap.data.models.PlaceModel
import com.google.gson.annotations.SerializedName

data class PlaceJson (
    @SerializedName("place_id"     ) var placeId     : String?           = null,
    @SerializedName("licence"      ) var licence     : String?           = null,
    @SerializedName("osm_type"     ) var osmType     : String?           = null,
    @SerializedName("osm_id"       ) var osmId       : String?           = null,
    @SerializedName("boundingbox"  ) var boundingbox : ArrayList<String> = arrayListOf(),
    @SerializedName("lat"          ) var lat         : String?           = null,
    @SerializedName("lon"          ) var lon         : String?           = null,
    @SerializedName("display_name" ) var displayName : String?           = null,
    @SerializedName("class"        ) var placeClass       : String?      = null,
    @SerializedName("type"         ) var type        : String?           = null,
    @SerializedName("importance"   ) var importance  : Double?           = null
) {
    fun toPlaceModel(): PlaceModel {
        return PlaceModel(
            placeId = placeId ?: "",
            displayName = displayName ?: "",
            latitude = lat?.toDoubleOrNull() ?: 0.0,
            longitude = lon?.toDoubleOrNull() ?: 0.0,
            type = type ?: ""
        )
    }
}