package com.ruslan.doordashlite.data

import com.ruslan.doordashlite.constants.DoorDashConstants.RESTAURANT_ENDPOINT
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.data.model.RestaurantDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DoorDashApi {

    @GET(RESTAURANT_ENDPOINT)
    suspend fun fetchAllRestaurants(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Response<List<Restaurant>>

    @GET("$RESTAURANT_ENDPOINT{id}/")
    suspend fun fetchRestaurant(
        @Path("id") restaurantId: Int)
            : Response<RestaurantDetails>
}