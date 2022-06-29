package com.star.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

interface IRepoModule {
    val moshi: Moshi
}

class RepoModule : IRepoModule {
    override val moshi: Moshi get() =
        Moshi.Builder()
                .add(ColorAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()
}