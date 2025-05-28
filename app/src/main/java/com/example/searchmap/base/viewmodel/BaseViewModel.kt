package com.example.searchmap.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchmap.base.network.BaseNetworkException
import com.example.searchmap.base.network.NetworkErrorException
import com.example.searchmap.base.network.NetworkResult


open class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    protected fun showLoading(show: Boolean) {
        _isLoading.postValue(show)
    }

    protected fun handleException(e: Throwable) {
        when (e) {
            is BaseNetworkException -> _error.postValue(e.message ?: "Có lỗi xảy ra")
            is NetworkErrorException -> _error.postValue(e.message ?: "Không có kết nối mạng")
            else -> _error.postValue(e.message ?: "Lỗi không xác định")
        }
    }
}