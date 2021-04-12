package com.ruslan.doordashlite.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ruslan.doordashlite.R
import com.ruslan.doordashlite.data.model.Restaurant
import kotlinx.android.synthetic.main.fragment_restaurant_details.*
import kotlinx.android.synthetic.main.restaurant_list_item.view.*

class RestaurantsListAdapter(
    private val restaurantList: ArrayList<Restaurant>,
    private val onRestaurantSelected: OnRestaurantSelected) :
    RecyclerView.Adapter<RestaurantsListAdapter.RestaurantViewHolder>() {

    interface OnRestaurantSelected {
        fun onRestaurantItemClick(restaurant: Restaurant)
    }

    fun addAll(restaurantListView: List<Restaurant>) {
        restaurantList.clear()
        restaurantList.addAll(restaurantListView);
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantsListAdapter.RestaurantViewHolder {
        return RestaurantViewHolder(
            (LayoutInflater.from(parent.context)
                .inflate(R.layout.restaurant_list_item, parent, false))
        )
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(
        holder: RestaurantsListAdapter.RestaurantViewHolder,
        position: Int
    ) {
        holder.bind(restaurantList[position])
    }

    inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(restaurant: Restaurant) {
            with(itemView) {
                Glide.with(this)
                    .load(restaurant.coverImgUrl)
                    .into(restaurantListImage)

                restaurant_name.text = restaurant.name
                restaurant_description.text = restaurant.description
                restaurant_status.text = restaurant.status
                
                setOnClickListener {
                    onRestaurantSelected.onRestaurantItemClick(restaurant)
                }
            }
        }
    }

}