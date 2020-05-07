package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseInvestmentInfo {

    @SerializedName("clientNumber")
    @Expose
    var clientNumber: Int? = null
    @SerializedName("totalPortfolio")
    @Expose
    var totalPortfolio: Int? = null
    @SerializedName("cash")
    @Expose
    var cash: Double? = null
    @SerializedName("holdingValue")
    @Expose
    var holdingValue: Int? = null
    @SerializedName("totalAssets")
    @Expose
    var totalAssets: Int? = null
    @SerializedName("totalLiabilities")
    @Expose
    var totalLiabilities: Int? = null
    @SerializedName("yesterdayPerformance")
    @Expose
    var yesterdayPerformance: Int? = null
    @SerializedName("mtdPerformance")
    @Expose
    var mtdPerformance: Int? = null
    @SerializedName("ytdPerformance")
    @Expose
    var ytdPerformance: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}