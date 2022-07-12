package com.star.data.api.response

data class TwZipCode(val cities: List<City>)

data class City(val name: String? = "", val code: Int? = -1, val region: List<Region>? = listOf())

data class Region(val name: String? = "", val code: Int? = -1)
