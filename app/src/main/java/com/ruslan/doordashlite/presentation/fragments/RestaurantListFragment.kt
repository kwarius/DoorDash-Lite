package com.ruslan.doordashlite.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruslan.doordashlite.R
import com.ruslan.doordashlite.data.model.Restaurant
import com.ruslan.doordashlite.databinding.FragmentRestaurantListBinding
import com.ruslan.doordashlite.presentation.viewmodel.RestaurantViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantListFragment : Fragment(), RestaurantsListAdapter.OnRestaurantSelected {

    private val viewModel: RestaurantViewModel  by viewModel()
    private lateinit var restaurantViewer: RecyclerView
    private var restaurantsListAdapter: RestaurantsListAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRestaurantListBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_restaurant_list, container, false)
        binding.restaurantViewModel = viewModel
        restaurantViewer = binding.root.findViewById(R.id.restaurantRecyclerView)
        restaurantViewer.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurantsListAdapter = RestaurantsListAdapter(arrayListOf(), this)
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        restaurantViewer.layoutManager = linearLayoutManager
        restaurantViewer.adapter = restaurantsListAdapter

        viewModel.getAllRestaurants().observe(viewLifecycleOwner, Observer { restaurantsList ->
            if (restaurantsList != null) {
                restaurantsListAdapter?.addAll(restaurantsList)
            }
        })

    }

    override fun onRestaurantItemClick(restaurant: Restaurant) {
        val directions =
            RestaurantListFragmentDirections.actionRestaurantListFragmentToRestaurantDetailsFragment(
                restaurantId = restaurant.id
            )
        findNavController().navigate(directions)
    }
}