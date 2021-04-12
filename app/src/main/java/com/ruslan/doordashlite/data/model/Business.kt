package com.ruslan.doordashlite.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Business(

    @SerializedName("id")
    @Expose
    private val id: Int,

    @SerializedName("name")
    @Expose
    private val name: String
)
