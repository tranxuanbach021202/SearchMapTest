package com.example.searchmap.data.service

import com.example.searchmap.base.network.BaseApiService
import com.example.searchmap.base.network.NetworkResult
import com.example.searchmap.data.api.LocationIQApi
import com.example.searchmap.data.modelJson.PlaceJson
import javax.inject.Inject

class PlaceApiService @Inject constructor(
    private val locationIQApi: LocationIQApi
) : BaseApiService() {

    suspend fun searchPlaces(query: String) : NetworkResult<List<PlaceJson>> {
        return callApi {
            locationIQApi.searchPlaces(query)
        }
    }
}