package com.ruslan.doordashlite.modules

import com.ruslan.doordashlite.presentation.viewmodel.RestaurantViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantViewModel = module {
    viewModel { RestaurantViewModel(get()) }
}
