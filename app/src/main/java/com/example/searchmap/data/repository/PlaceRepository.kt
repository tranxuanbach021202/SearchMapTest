package com.example.searchmap.data.repository

import com.example.searchmap.data.models.PlaceModel
import com.example.searchmap.base.network.NetworkResult
import com.example.searchmap.data.service.PlaceApiService
import com.example.searchmap.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRepository @Inject constructor (
    private val apiService: PlaceApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun searchPlaces(query: String): List<PlaceModel> = withContext(dispatcher) {
        when(val result = apiService.searchPlaces(query)) {
            is NetworkResult.Success ->{
                result.data.map { it.toPlaceModel() }
            }

            is NetworkResult.Error -> {
                throw result.exception
            }
        }
    }

}