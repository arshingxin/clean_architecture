package com.star.domain.model

import com.squareup.moshi.Json
import com.star.data.api.response.DeviceInfo

private val DEBUG = false
private val TAG = "DeviceInfo"

fun DeviceInfo.toDeviceInfoModel() = DeviceInfoModel(
    id,
    name,
    sn,
    testMode,
    isStylistOn,
    isScalpDetectionOn,
    isPowerSavingOn,
    isQuickCut,
    powerSavingParameter,
    storeId,
    areaId,
    cityId,
    areaZipCode,
    storeName,
    headquarterId,
    isYahooVastOn,
    isClickforceVastOn,
    isHivestackVastOn,
    vastUrl,
    yahooVastUrl,
    clickforceVastUrl,
    hivestackVastUrl,
    vastRequestedFrequency,
    vendor,
    version,
    programLength
)

data class DeviceInfoModel(
    @Json(name = "id") val id: Int = -1,
    @Json(name = "name") val name: String = "",
    @Json(name = "sn") val sn: String? = "",
    @Json(name = "test_mode") val testMode: Boolean = false,
    @Json(name = "is_stylist_on") val isStylistOn: Boolean? = false,
    @Json(name = "is_scalp_detection_on") val isScalpDetectionOn: Boolean? = false,
    @Json(name = "is_power_saving_on") val isPowerSavingOn: Boolean? = false,
    @Json(name = "is_quick_cut") val isQuickCut: Boolean? = false,
    @Json(name = "power_saving_parameter") val powerSavingParameter: Double? = 0.2,
    @Json(name = "store_id") val storeId: Int = -1,
    @Json(name = "area_id") val areaId: Int? = -1,
    @Json(name = "city_id") val cityId: Int? = -1,
    @Json(name = "area_zip_code") val areaZipCode: Int? = -1,
    @Json(name = "store_name") val storeName: String = "",
    @Json(name = "headquarter_id") val headquarterId: Int? = -1,
    /**
     * vast 相關 start
     */
    @Json(name = "is_yahoo_vast_on") val isYahooVastOn: Boolean? = false,
    @Json(name = "is_clickforce_vast_on") val isClickforceVastOn: Boolean? = false,
    @Json(name = "is_hivestack_vast_on") val isHivestackVastOn: Boolean? = false,
    @Json(name = "vast_url") val vastUrl: String? = "",
    @Json(name = "yahoo_vast_url") val yahooVastUrl: String? = null,
    @Json(name = "clickforce_vast_url") val clickforceVastUrl: String? = null,
    @Json(name = "hivestack_vast_url") val hivestackVastUrl: String? = null,
    @Json(name = "vast_requested_frequency") val vastRequestedFrequency: Int? = 4,
    /**
     * vast 相關 end
     */
    // 供應商，asus: "華碩", hws: "漢維視", owatis: "歐華帝斯"
    @Json(name = "vendor") val vendor: String? = "",
    @Json(name = "version") val version: Int? = -1,
    // 每多久出現一次廣告
    @Json(name = "program_length") val programLength: Int? = -1,
    @Json(name = "tabs") val tabs: List<TabInfoModel>? = listOf(),
    @Json(name = "periods") val periods: List<PeriodInfo>? = listOf(),
    @Json(name = "latest_app_sn") val latestMirrorVersion: String? = "",
    @Json(name = "latest_launcher_sn") val latestLauncherVersion: String? = "",
    @Json(name = "latest_test_app_sn") val latestTestMirrorVersion: String? = "",
    @Json(name = "latest_test_launcher_sn") val latestTestLauncherVersion: String? = "",
    @Json(name = "is_enable_play_inner_banner") val isEnablePlayInnerBanner: Boolean? = false,
    @Json(name = "inner_banner_frequency") val innerBannerFrequency: Long? = 30L,
    @Json(name = "is_enable_play_inner_ad") val isEnablePlayInnerAd: Boolean? = false,
    @Json(name = "inner_ad_frequency") val innerAdFrequency: Long? = 1800L,
    @Json(name = "is_face_recognition_on") val isFaceRecognitionOn: Boolean? = false,
    @Json(name = "lowest_ads_amount") val lowestAdsAmount: Int? = 15
)

data class TabInfoModel(
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String? = null,
    @Json(name = "channel") val channel: String? = null,
    @Json(name = "items") val items: List<AboutInfoModel>? = listOf(),
    @Json(name = "categories") val categories: List<CategoryInfo>? = listOf(),
    @Json(name = "version") val version: Long? = -1L,
    var lastUpdateTime: Long? = 0L,
    var tabIndex: Int? = -1,
    var brandEvent: BrandEventModel? = null,
    var isAboutTab: Boolean? = false,
    var isChannelTab: Boolean? = false,
    var isShopTab: Boolean? = false,
    var isBrandTab: Boolean? = false,
    var isCustomBrandTab: Boolean? = false,
    var isScalpTab: Boolean? = false,
    var isHairArTab: Boolean? = false,
    var isMakeupsArTab: Boolean? = false,
    var isGameTab: Boolean? = false,
    var assistantFunctionName: String? = null
) {
    fun clone() = TabInfoModel(
        name,
        type,
        channel,
        items,
        categories,
        version,
        lastUpdateTime,
        tabIndex,
        brandEvent,
        isAboutTab,
        isChannelTab,
        isShopTab,
        isBrandTab,
        isCustomBrandTab,
        isScalpTab,
        isHairArTab,
        isMakeupsArTab,
        isGameTab,
        assistantFunctionName
    )
}

data class CategoryInfo(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "sub_title") val subTitle: String? = "",
    @Json(name = "is_default") val isDefault: Boolean? = false,
    @Json(name = "programs_quantity") var programsQuantity: Int? = 0,
    @Json(name = "cover_photo_url") val coverPhotoUrl: String? = "",
    @Json(name = "cover_photo_md5") var coverPhotoMd5: String? = "",
    @Json(name = "version") val version: Long? = -1L,
    @Json(name = "start_at") val startAt: String? = "",
    @Json(name = "end_at") val endAt: String? = "",
    var type: String? = "",
    var localCoverPhotoUrl: String?,
    var fileExt: String?,
    var localFolderPath: String = "",
    var lastUpdateTime: Long? = 0L,
    var localVersion: Long? = -1L,
    // 預設都需要下載
    var needToDownload: Boolean = true
)

data class AboutInfoModel(
    @Json(name = "id") val id: Int? = -1,
    @Json(name = "name") val name: String?,
    @Json(name = "url") val url: String? = "",
    @Json(name = "md5") val md5: String? = "",
    @Json(name = "version") val version: Long? = 0L,
    @Json(name = "video") val videoUrl: String? = "",
    @Json(name = "video_md5") val videoMd5: String? = ""
)

data class ImageData(
    @Json(name = "url") val url: String? = "",
    @Json(name = "md5") var md5: String? = "",
    @Json(name = "version") val version: Long? = 0L,
    @Json(name = "name") var name: String? = "",
    @Json(name = "id") val id: Int? = -1,
    @Json(name = "video") val videoUrl: String? = "",
    @Json(name = "video_md5") var videoMd5: String? = "",
    var type: String? = "",
    var localUrl: String? = "",
    var fileExt: String? = "",
    var localFolderPath: String = "",
    var localVideoUrl: String? = "",
    var localVideoFolderPath: String = "",
    var localVideoIconUrl: String? = "",
    var localVideoIconFolderPath: String = "",
    var isIcon: Boolean? = false,
    var lastUpdateTime: Long? = 0,
    var localVersion: Long? = -1L,
    // 預設都需要下載
    var needToDownload: Boolean? = true
) {
    fun clone() = ImageData(
        url,
        md5,
        version,
        name,
        id,
        videoUrl,
        videoMd5,
        type,
        localUrl,
        fileExt,
        localFolderPath,
        localVideoUrl,
        localVideoFolderPath,
        localVideoIconUrl,
        localVideoIconFolderPath,
        isIcon,
        lastUpdateTime,
        localVersion,
        needToDownload
    )
}

data class PeriodInfo(
    @Json(name = "day") val day: String? = null,
    @Json(name = "boot") val boot: String? = null,
    @Json(name = "shutdown") val shutdown: String? = null
)