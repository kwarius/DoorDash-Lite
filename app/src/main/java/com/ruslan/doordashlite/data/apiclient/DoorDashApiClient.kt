package com.ruslan.doordashlite.data.apiclient
import com.ruslan.doordashlite.data.base.Resource
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.data.model.RestaurantDetails

interface DoorDashApiClient {

    suspend fun getAllRestaurants(latitude: Double, longitude: Double): Resource<List<Restaurant>>

    suspend fun getRestaurantInfo(restaurantId: Int): Resource<RestaurantDetails>

}