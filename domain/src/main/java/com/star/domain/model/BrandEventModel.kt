package com.star.domain.model

import com.squareup.moshi.Json

data class BrandEventModel(
    @Json(name = "id") var id: Int,
    @Json(name = "name") var name: String,
    @Json(name = "image_url") var imageUrl: String? = "",
    @Json(name = "image_md5") var imageMd5: String? = "",
    @Json(name = "start_at") var startAt: String? = null,
    @Json(name = "end_at") var endAt: String? = null,
    var mapKey: String?,
    var localImageUrl: String?
)

//"id": 1,
//"attachment_url": "https://storage.googleapis.com/cdn.imojing.cc/uploads/banner/attachment/1/9161ada90c7ba395.png",
//"attachment_md5": "cfca74a33cdea591ce285a03645f6362",
//"linking_type": "product_category", // 點擊後連結到商品類別或商品
//"product_id": null,
//"product_category_id": 1
data class BrandBanner(
    @Json(name = "id") val id: Int,
    @Json(name = "attachment_url") val attachmentUrl: String,
    @Json(name = "attachment_md5") val attachmentMd5: String,
    @Json(name = "linking_type") val linkingType: String? = "",
    @Json(name = "product_id") val productId: Int? = -1,
    @Json(name = "product_category_id") val productCategoryId: Int? = -1,
    @Json(name = "start_at") var startAt: String? = null,
    @Json(name = "end_at") var endAt: String? = null,
    var brandId: Int?,
    var brandName: String?,
    var mapKey: String?,
    var index: Int? = 0,
    var localUrl: String? = null
)

//"id": 45,
//"name": "category 14",
//"logo_url": "/uploads/product/category/logo/45/3820d18d1cbfeacd.png",
//"logo_md5": "9591c410148e6883727c5339fd1c02cd"
data class BrandProductionCategory (
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "logo_url") val logoUrl: String,
    @Json(name = "logo_md5") val logoMd5: String,
    @Json(name = "start_at") var startAt: String? = null,
    @Json(name = "end_at") var endAt: String? = null,
    var brandId: Int?,
    var brandName: String?,
    var mapKey: String?,
    var index: Int? = 0,
    var localLogoUrl: String? = ""
)