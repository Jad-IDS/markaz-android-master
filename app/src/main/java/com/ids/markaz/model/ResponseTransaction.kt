package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseTransaction {

    @SerializedName("systemId")
    @Expose
    var systemId: String? = null
    @SerializedName("transactionDate")
    @Expose
    var transactionDate: String? = null
    @SerializedName("transactionTypeAr")
    @Expose
    var transactionTypeAr: String? = null
    @SerializedName("transactionTypeEn")
    @Expose
    var transactionTypeEn: String? = null
    @SerializedName("productNo")
    @Expose
    var productNo: String? = null
    @SerializedName("shortName")
    @Expose
    var shortName: Any? = null
    @SerializedName("securityCode")
    @Expose
    var securityCode: String? = null
    @SerializedName("securityShortName")
    @Expose
    var securityShortName: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("unitPrice")
    @Expose
    var unitPrice: Double? = null
    @SerializedName("valueDate")
    @Expose
    var valueDate: String? = null
    @SerializedName("valueAmount")
    @Expose
    var valueAmount: String? = null
    @SerializedName("valueCurrency")
    @Expose
    var valueCurrency: String? = null
    @SerializedName("settlementDate")
    @Expose
    var settlementDate: String? = null
    @SerializedName("settlementAmount")
    @Expose
    var settlementAmount: String? = null
    @SerializedName("settlementCurrency")
    @Expose
    var settlementCurrency: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}