package com.ruslan.doordashlite.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

private const val FREE = "Free delivery!"

data class RestaurantDetails(
    @SerializedName("is_time_surging")
    @Expose
    val isTimeSurging: Boolean,

    @SerializedName("max_order_size")
    @Expose
    val maxOrderSize: Any,

    @SerializedName("delivery_fee")
    @Expose
    val deliveryFee: Int,

    @SerializedName("max_composite_score")
    @Expose
    val maxCompositeScore: Int,

    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("merchant_promotions")
    @Expose
    val merchantPromotions: List<Any>,

    @SerializedName("average_rating")
    @Expose
    val averageRating: Double,

    @SerializedName("menus")
    @Expose
    val menus: List<Menu>,

    @SerializedName("composite_score")
    @Expose
    val compositeScore: Int,

    @SerializedName("status_type")
    @Expose
    val statusType: String,

    @SerializedName("is_only_catering")
    @Expose
    val isOnlyCatering: Boolean,

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("number_of_ratings")
    @Expose
    val numberOfRatings: Int,

    @SerializedName("asap_time")
    @Expose
    val asapTime: Int,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("business")
    @Expose
    val business: Business,

    @SerializedName("tags")
    @Expose
    val tags: List<String>,

    @SerializedName("yelp_review_count")
    @Expose
    val yelpReviewCount: Int,

    @SerializedName("business_id")
    @Expose
    val businessId: Int,

    @SerializedName("extra_sos_delivery_fee")
    @Expose
    val extraSosDeliveryFee: Int,

    @SerializedName("yelp_rating")
    @Expose
    val yelpRating: Double,

    @SerializedName("cover_img_url")
    @Expose
    val coverImgUrl: String,

    @SerializedName("header_img_url")
    @Expose
    val headerImgUrl: String,

    @SerializedName("address")
    @Expose
    val address: Address,

    @SerializedName("price_range")
    @Expose
    val priceRange: Int,

    @SerializedName("slug")
    @Expose
    val slug: String,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("is_newly_added")
    @Expose
    val isNewlyAdded: Boolean,

    @SerializedName("url")
    @Expose
    val url: String,

    @SerializedName("service_rate")
    @Expose
    val serviceRate: Double,

    @SerializedName("promotion")
    @Expose
    val promotion: Any,

    @SerializedName("featured_category_description")
    @Expose
    val featuredCategoryDescription: Any
) {
    fun getDeliveryFees(): String? {
        return if (deliveryFee == 0) {
            FREE
        } else {
            "Delivery Fee: $" + (deliveryFee / 100.0).toString()
        }
    }
}