package com.ruslan.doordashlite.presentation.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ruslan.doordashlite.R
import com.ruslan.doordashlite.data.model.RestaurantDetails
import com.ruslan.doordashlite.databinding.FragmentRestaurantDetailsBinding
import com.ruslan.doordashlite.presentation.viewmodel.RestaurantViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_restaurant_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantDetailsFragment : Fragment() {

    lateinit var rootView: View
    private val viewModel: RestaurantViewModel  by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRestaurantDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_details, container, false)
        binding.restaurantViewModel = viewModel
        rootView =  binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val restaurantId = RestaurantDetailsFragmentArgs.fromBundle(it).restaurantId
            viewModel.fetchRestaurantDetails(restaurantId).observe(viewLifecycleOwner, Observer { restaurant ->
                if(restaurant != null) { //handle error use cases
                    displayRestaurantDetails(restaurant)
                } else {
                    displayErrorMessage(restaurant)
                }
            })
        }
    }

    private fun displayRestaurantDetails(restaurantDetails: RestaurantDetails) {
        Glide.with(requireActivity())
            .load(restaurantDetails.coverImgUrl)
            .into(restaurantImage)

        restaurantName.text = restaurantDetails.name
        restaurantDescription.text = restaurantDetails.description
        restaurantRatingStar.rating = restaurantDetails.averageRating.toFloat()
        restaurantDeliveryFee.text= restaurantDetails.getDeliveryFees()
        restaurantAddress.text = restaurantDetails.address.printableAddress
        restaurantStatus.text = restaurantDetails.status

        Glide.with(requireActivity())
            .load(restaurantDetails.headerImgUrl)
            .into(restaurantHeaderImage)
    }

    private fun displayErrorMessage(error: Throwable?) {
        if (error != null) {
            print(error.localizedMessage)
        }
        Snackbar.make(rootView, getString(R.string.error_fetching_details), Snackbar.LENGTH_SHORT).show()
    }
}