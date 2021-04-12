package com.ruslan.doordashlite.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    @Expose
    val city: String,

    @SerializedName("state")
    @Expose
    val state: String,

    @SerializedName("street")
    @Expose
    val street: String,

    @SerializedName("lat")
    @Expose
    val lat: Double,

    @SerializedName("lng")
    @Expose
    val lng: Double,

    @SerializedName("printable_address")
    @Expose
    val printableAddress: String
)