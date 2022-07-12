package com.star.domain.model

import com.star.data.api.response.MyItemInfo

data class MyItemInfoModel(val title: String? = "", val items: MutableList<String>? = mutableListOf())

fun MyItemInfo.toMyItemInfoModel() = MyItemInfoModel(title, items)
