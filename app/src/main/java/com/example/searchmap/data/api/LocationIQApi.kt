package com.example.searchmap.data.api

import com.example.searchmap.data.modelJson.PlaceJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationIQApi {

    @GET("search")
    suspend fun searchPlaces(
        @Query("q") query: String
    ): Response<List<PlaceJson>>
}