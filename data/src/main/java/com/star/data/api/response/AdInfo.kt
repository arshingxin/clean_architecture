package com.star.data.api.response

import com.squareup.moshi.Json

data class AdInfo(
    @Json(name = "id") val id: Int? = -1,
    @Json(name = "name") var name: String? = "",
    @Json(name = "scanning_link") var qrCodeLink: String? = "",
    @Json(name = "seconds") var seconds: Int? = 30, // 廣告撥放時間
    @Json(name = "advertiser_id") var advertiserId: Int? = -1,
    /**
     * 'carousel' 輪播
     * 'precise' (Note: 屬於此type, 要檢查target_audience是否有符合才撥放)
     * 'reserve' 墊檔
     */
    @Json(name = "play_type") var playType: String? = "",
    @Json(name = "material") val material: String? = "", // 廣告下載連結
    @Json(name = "material_md5") var materialMd5: String? = "", // 廣告MD5
    @Json(name = "banner") var banner: String? = "", // 右邊banner
    @Json(name = "banner_md5") var bannerMd5: String? = "", // 右邊banner md5
    // 精準投放
    @Json(name = "precise_target_audience") var preciseTargetAudience: PreciseTargetAudience? = null,
    // 目標客群
    @Json(name = "target_audience") var targetAudience: TargetAudience? = null,
    @Json(name = "version") val version: Long? = 0L,
    @Json(name = "is_collect_data") var isCollectData: Boolean? = false,
    @Json(name = "impression_tag") var impressionTAG: String? = "",
    @Json(name = "view_tag") var viewTAG: String? = "",
    @Json(name = "start_at") var startAt: String? = "",
    @Json(name = "end_at") var endAt: String? = "",
    @Json(name = "live_stream_link") var liveStreamLink: String? = "",
    @Json(name = "live_start_at") var liveStartAt: String? = "",
    @Json(name = "live_end_at") var liveEndAt: String? = "",
    @Json(name = "live_front_cover") var liveFrontCover: LiveFrontCover? = null,
    @Json(name = "live_hash_tag") var hashTagList: List<String>? = listOf(),
    @Json(name = "live_banners") var liveBanners: List<LiveBanner>? = listOf(),
    @Json(name = "gender") var gender: String? = "", // or male or null,
    @Json(name = "played_sessions") var playedSessions: List<PlayedSessionData>? = listOf(),
    @Json(name = "age_groups") var ageGroups: List<String>? = listOf(),
    // 是否超出廣告預算 true:超出,不可撥放廣告, false:otherwise
    @Json(name = "is_over_budget") var is_over_budget: Boolean? = false,
    @Json(name = "area_ids") var areaIds: List<Int>? = listOf(),
    var materialLocalPathType: String? = "",
    var bannerLocalPathType: String? = "",
    var localMaterial: String? = "",
    var localMaterialVersion: Long? = -1L,
    var isVideo: Boolean? = false,
    var localBanner: String? = "",
    var localBannerVersion: Long? = -1L,
    var fileExt: String? = "",
    var orderNum: Int? = 0,
    // 檔案最近被使用的時間 ms
    var lastUpdateTime: Long? = 0L,
    // 預設都需要下載
    var needToDownload: Boolean? = true,
    // 是否屬於精準廣告
    var isPrecise: Boolean? = false,
    var mapKey: String? = "",
    // 是否已經回報下載進度
    var hasReportProgress: Boolean? = false,
    // 需要補墊檔
    var needReverseAdInfo: Boolean? = false,
    // 屬於live
    var isLive: Boolean? = false
)

data class LiveFrontCover(
    @Json(name = "id") val id: Int? = -1,
    @Json(name = "attachment_md5") var attachmentMd5: String? = "",
    @Json(name = "attachment_url") var attachmentUrl: String? = "",
    var localFrontCover: String? = ""
)

data class PlayedSessionData(
    @Json(name = "start_at") val startAt: String? = "",
    @Json(name = "end_at") val endAt: String? = ""
)

data class PreciseTargetAudience(
    @Json(name = "gender") val gender: String? = "",
    @Json(name = "ages") val ages: List<String>? = listOf()
)

data class LiveBanner(
    @Json(name = "id") val id: Int? = -1,
    @Json(name = "seconds") var seconds: Int? = 10,
    @Json(name = "attachment_md5") var attachmentMd5: String? = "",
    @Json(name = "attachment_url") var attachmentUrl: String? = "",
    var localBanner: String? = ""
)

data class TargetAudience(
    @Json(name = "gender") val gender: String? = "",
    @Json(name = "age") val age: String? = "",
    @Json(name = "location") val location: String? = "",
    @Json(name = "time") val time: String? = ""
)
