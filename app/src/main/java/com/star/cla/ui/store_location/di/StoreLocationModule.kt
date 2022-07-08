package com.star.cla.ui.store_location.di

import com.star.cla.ui.store_location.StoreLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val storeLocationModule = module {
    viewModel { StoreLocationViewModel() }
}