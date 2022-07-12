package com.star.cla.ui.my.user_info.di

import com.star.cla.ui.my.user_info.UserInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userInfoModule = module {
    viewModel { UserInfoViewModel() }
}