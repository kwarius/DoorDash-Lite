package com.ruslan.doordashlite

import com.ruslan.doordashlite.data.model.Restaurant
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import junit.framework.Assert
import org.junit.Test
import java.io.InputStreamReader
import java.io.Reader
import java.util.*

class RestaurantTest {

    @Test
    fun gsonDeserialization() {
        val inputStream =
            Objects.requireNonNull(this.javaClass.classLoader)
                .getResourceAsStream("restaurant5.txt")
        val reader: Reader = InputStreamReader(inputStream, "UTF-8")
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val restaurant = gson.fromJson(reader, Restaurant::class.java)
        Assert.assertNotNull(restaurant)
        Assert.assertEquals(5, restaurant.id)
        Assert.assertEquals("Amarin Thai Cuisine (Mountain View)", restaurant.name)
        Assert.assertEquals("Thai Cuisine", restaurant.description)
        Assert.assertEquals(
            "https://cdn.doordash.com/media/restaurant/cover/A  marin-Thai-Cuisine.png",
            restaurant.coverImgUrl
        )
        Assert.assertEquals("Pre-order for 12:45PM", restaurant.status)
        assertDeliveryFeeFree(restaurant)
        assertRating(restaurant, 4.6, 3927)
    }

    @Test
    fun displayDeliveryFee() {
        val restaurant = Restaurant()
        assertDeliveryFeeFree(restaurant)
        restaurant.setDeliveryFee(1)
        assertDeliveryFee(restaurant, 1, "$0.01")
        restaurant.setDeliveryFee(50)
        assertDeliveryFee(restaurant, 50, "$0.50")
        restaurant.setDeliveryFee(199)
        assertDeliveryFee(restaurant, 199, "$1.99")
        restaurant.setDeliveryFee(205)
        assertDeliveryFee(restaurant, 205, "$2.05")
        restaurant.setDeliveryFee(-100)
        assertDeliveryFee(restaurant, -100, "FREE")
        restaurant.setDeliveryFee(1500)
        assertDeliveryFee(restaurant, 1500, "$15.00")
        restaurant.setDeliveryFee(0)
        assertDeliveryFeeFree(restaurant)
    }

    @Test
    fun displayRating() {
        val restaurant = Restaurant()
        assertRating(restaurant, 0.0, 0)
        restaurant.setNumberOfRatings(1)
        assertRating(restaurant, 0.0, 1)
        restaurant.setAverageRating(5.0)
        assertRating(restaurant, 5.0, 1)
        restaurant.setAverageRating(3.9)
        restaurant.setNumberOfRatings(50712)
        assertRating(restaurant, 3.9, 50712)
        restaurant.setAverageRating(2.000001)
        restaurant.setNumberOfRatings(-3)
        assertRating(restaurant, 2.000001, -3)
        restaurant.setAverageRating(-9.2)
        restaurant.setNumberOfRatings(0)
        assertRating(restaurant, -9.2, 0)
        restaurant.setAverageRating(0.0)
        assertRating(restaurant, 0.0, 0)
    }

    private fun assertDeliveryFeeFree(restaurant: Restaurant) {
        assertDeliveryFee(restaurant, 0, "FREE")
    }

    private fun assertDeliveryFee(
        restaurant: Restaurant,
        expectedDeliveryFee: Int,
        expectedDisplayDeliveryFee: String
    ) {
        Assert.assertEquals(
            expectedDisplayDeliveryFee,
            restaurant.getDisplayDeliveryFee()
        )
    }

    private fun assertRating(
        restaurant: Restaurant,
        expectedAverageRating: Double,
        expectedNumberOfRatings: Int
    ) {
        Assert.assertEquals(
            expectedAverageRating,
            restaurant.getAverageRating()
        )
        Assert.assertEquals(
            expectedNumberOfRatings,
            restaurant.getNumberOfRatings()
        )
        Assert.assertEquals(
            "$expectedAverageRating / 5.0 ($expectedNumberOfRatings ratings)",
            restaurant.getDisplayRating()
        )
    }
}