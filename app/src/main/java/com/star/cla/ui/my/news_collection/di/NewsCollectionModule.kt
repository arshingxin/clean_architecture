package com.star.cla.ui.my.news_collection.di

import com.star.cla.ui.my.news_collection.NewsCollectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsCollectionModule = module {
    viewModel { NewsCollectionViewModel() }
}