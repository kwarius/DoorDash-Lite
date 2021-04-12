package com.ruslan.doordashlite.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LAT
import com.ruslan.doordashlite.constants.DoorDashConstants.DEFAULT_LNG
import com.ruslan.doordashlite.data.base.Status
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.data.model.RestaurantDetails
import com.ruslan.doordashlite.data.apiclient.DoorDashApiClient
import kotlinx.coroutines.launch

class RestaurantViewModel(private val doorDashApiClient: DoorDashApiClient) : ViewModel() {

    val isWaiting: ObservableField<Boolean> = ObservableField()
    val errorMessage: ObservableField<String> = ObservableField()
    val loadStateLiveData: MutableLiveData<Status> = MutableLiveData()
    private val restaurantList: ObservableField<List<Restaurant>> = ObservableField()
    private var restaurantListLiveData: MutableLiveData<List<Restaurant>> = MutableLiveData()
    private val restaurantDetails: ObservableField<RestaurantDetails> = ObservableField()
    private var restaurantDetailsLiveData: MutableLiveData<RestaurantDetails> = MutableLiveData()

    init {
        isWaiting.set(true)
        errorMessage.set(null)
        restaurantListLiveData = fetchAllRestaurantsList()
    }

    fun getAllRestaurants() : LiveData<List<Restaurant>>{
        return restaurantListLiveData
    }

    private fun fetchAllRestaurantsList(): MutableLiveData<List<Restaurant>> {
        viewModelScope.launch {
            loadStateLiveData.postValue(Status.LOADING)
            val result = doorDashApiClient.getAllRestaurants(DEFAULT_LAT, DEFAULT_LNG)
            if (result.status == Status.SUCCESS) {
                restaurantList.set(result.data)
                errorMessage.set(null)
                restaurantListLiveData.value = result.data
            } else {
                restaurantList.set(null)
                if (result.data.isNullOrEmpty()) {
                    loadStateLiveData.postValue(Status.EMPTY)
                    errorMessage.set("Something went wrong, please try again later :(")
                } else {
                    loadStateLiveData.postValue(Status.ERROR)
                    errorMessage.set(result.message)
                }
            }
            isWaiting.set(false)
        }
        return restaurantListLiveData
    }

    fun fetchRestaurantDetails(restaurantId: Int): MutableLiveData<RestaurantDetails> {
        viewModelScope.launch {
            if(isWaiting.get()!!){
                val result = doorDashApiClient.getRestaurantInfo(restaurantId)
                if (result.status == Status.SUCCESS) {
                    restaurantDetails.set(result.data)
                    errorMessage.set(null)
                    restaurantDetailsLiveData.value = result.data
                } else {
                    restaurantDetails.set(null)
                    if (result.data == null) {
                        errorMessage.set("Error fetching restaurant details :(")
                    } else {
                        errorMessage.set(result.message)
                    }
                }
                isWaiting.set(false)
            }
        }
        return restaurantDetailsLiveData
    }
}