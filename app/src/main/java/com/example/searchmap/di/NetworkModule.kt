package com.example.searchmap.di

import com.example.searchmap.data.api.ApiConfig
import com.example.searchmap.data.api.LocationIQApi
import com.example.searchmap.data.service.PlaceApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideApiConfig(): ApiConfig {
        return ApiConfig
    }

    @Provides
    @Singleton
    fun provideLocationIQApi(apiConfig: ApiConfig): LocationIQApi {
        return apiConfig.locationApi
    }


}