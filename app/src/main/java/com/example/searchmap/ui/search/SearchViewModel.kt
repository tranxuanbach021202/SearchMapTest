package com.example.searchmap.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.searchmap.base.viewmodel.BaseViewModel
import com.example.searchmap.data.models.PlaceModel
import com.example.searchmap.data.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Normalizer
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : BaseViewModel() {

    private val _searchResults = MutableLiveData<List<PlaceModel>>()
    val searchResults: LiveData<List<PlaceModel>> get() = _searchResults

    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent


    private var searchJob: Job? = null
    private val debounceTime = 1000L // 1 giây debounce


    fun searchPlace(query: String) {


        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(debounceTime) // Debounce delay
            showLoading(true)
            try {
                    Log.d("SearchViewModel", "Search query: $query")
                    val results = placeRepository.searchPlaces(query)
                    Log.d("SearchViewModel", "Search results: $results")
                    _searchResults.postValue(results)
                } catch (e: Exception) {
                    handleException(e)
                } finally {
                    showLoading(false)
            }
        }

    }


    // clear kết quả tìm kiếm
    fun clearResults() {
        _searchResults.postValue(emptyList())
        showLoading(false)
    }


    // Click button direction
    fun onPlaceDirectionClick(place: PlaceModel) {
        _navigationEvent.value = NavigationEvent.OpenGoogleMaps(
            latitude = place.latitude,
            longitude = place.longitude
        )
    }



    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }

}

sealed class NavigationEvent {
    data class OpenGoogleMaps(val latitude: Double, val longitude: Double) : NavigationEvent()
}
