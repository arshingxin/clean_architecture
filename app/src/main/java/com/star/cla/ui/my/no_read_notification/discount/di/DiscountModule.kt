package com.star.cla.ui.my.no_read_notification.discount.di

import com.star.cla.ui.my.no_read_notification.discount.DiscountViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val discountModule = module {
    viewModel { DiscountViewModel() }
}