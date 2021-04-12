package com.ruslan.doordashlite.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("popular_items")
    @Expose
    private val popularItems: List<Any>,

    @SerializedName("is_catering")
    @Expose
    private val isCatering: Boolean,

    @SerializedName("subtitle")
    @Expose
    private val subtitle: String,

    @SerializedName("id")
    @Expose
    private val id: Int,

    @SerializedName("name")
    @Expose
    private val name: String
)
