package com.star.cla.ui.my.user_info.user_info_edit.di

import com.star.cla.ui.my.user_info.user_info_edit.UserInfoEditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userInfoEditModule = module {
    viewModel { UserInfoEditViewModel() }
}