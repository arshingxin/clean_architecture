package com.star.cla.ui.my.news_collection.news_edit.di

import com.star.cla.ui.my.news_collection.news_edit.NewsEditViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsEditModule = module {
    viewModel { NewsEditViewModel() }
}