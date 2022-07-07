package com.star.cla.ui.my.no_read_notification.pet.di

import com.star.cla.ui.my.no_read_notification.pet.PetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val petModule = module {
    viewModel { PetViewModel() }
}