package com.star.cla.ui.location.di

import com.star.cla.ui.location.LocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationModule = module {
    viewModel { LocationViewModel() }
}