package com.ruslan.doordashlite.data.apiclient

import com.ruslan.doordashlite.data.DoorDashApi
import com.ruslan.doordashlite.data.base.Resource
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.data.model.RestaurantDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoorDashApiClientImpl(private val doorDashApi: DoorDashApi) : DoorDashApiClient {

    override suspend fun getAllRestaurants(
        latitude: Double,
        longitude: Double
    ): Resource<List<Restaurant>> =
        withContext(Dispatchers.IO) {
            try {
                val response = doorDashApi.fetchAllRestaurants(latitude, longitude)
                if (response.isSuccessful) {
                    Resource.success(response.body())
                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error<List<Restaurant>>("${ex.message}")
            }
        }

    override suspend fun getRestaurantInfo(restaurantId: Int): Resource<RestaurantDetails> =
        withContext(Dispatchers.IO) {
            try {
                val response = doorDashApi.fetchRestaurant(restaurantId)
                if (response.isSuccessful) {
                    Resource.success(response.body())
                } else {
                    Resource.error(response.message())
                }
            } catch (ex: Throwable) {
                Resource.error<RestaurantDetails>("${ex.message}")
            }
        }
}