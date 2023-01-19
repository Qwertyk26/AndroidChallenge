package com.example.androidchallenge.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.data.usecases.GetPoiUseCase
import com.example.androidchallenge.di.DispatchersProvider
import com.example.androidchallenge.domain.models.poi.Poi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private var getPoiUseCase: GetPoiUseCase,
    private val dispatchersProvider: DispatchersProvider
): ViewModel() {

    private val _poiItems = MutableLiveData<List<Poi>>()
    val poiItems: LiveData<List<Poi>> get() = _poiItems
    val networkError = MutableLiveData(false)
    private var updateTimer: Job? = null

    fun getPoiItems(distance: Int, latitude: Double, longitude: Double) {
        viewModelScope.launch(dispatchersProvider.io) {
            val poiItems = mutableListOf<Poi>()
            val pois: List<Poi> = async {
                try {
                    val response = getPoiUseCase.call(distance, latitude, longitude)
                    if (response.isSuccessful) {
                        response.body()?.let {
                            return@async it
                        }
                    }
                } catch (ex: IOException) {
                    networkError.postValue(true)
                }
                return@async emptyList()
            }.await()
            poiItems.addAll(pois)
            _poiItems.postValue(poiItems)
        }
    }

    fun repeatRequest(distance: Int, latitude: Double, longitude: Double) {
        updateTimer = viewModelScope.launch(dispatchersProvider.io) {
            while (isActive) {
                getPoiItems(distance, latitude, longitude)
                delay(30000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        updateTimer?.cancel()
    }
}