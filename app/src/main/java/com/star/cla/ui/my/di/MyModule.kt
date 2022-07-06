package com.star.cla.ui.my.di

import com.star.cla.ui.my.MyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    viewModel { MyViewModel() }
}