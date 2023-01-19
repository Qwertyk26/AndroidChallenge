package com.example.androidchallenge.domain.models.poi


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddressInfo(
    @SerializedName("accessComments")
    var accessComments: String? = null,
    @SerializedName("addressLine1")
    var addressLine1: String? = null,
    @SerializedName("addressLine2")
    var addressLine2: String? = null,
    @SerializedName("contactEmail")
    var contactEmail: String? = null,
    @SerializedName("contactTelephone1")
    var contactTelephone1: String? = null,
    @SerializedName("contactTelephone2")
    var contactTelephone2: String? = null,
    @SerializedName("country")
    var country: Any? = null,
    @SerializedName("countryID")
    var countryID: Int? = null,
    @SerializedName("distance")
    var distance: Double? = null,
    @SerializedName("distanceUnit")
    var distanceUnit: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @SerializedName("postcode")
    var postcode: String? = null,
    @SerializedName("relatedURL")
    var relatedURL: String? = null,
    @SerializedName("stateOrProvince")
    var stateOrProvince: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("town")
    var town: String? = null
)