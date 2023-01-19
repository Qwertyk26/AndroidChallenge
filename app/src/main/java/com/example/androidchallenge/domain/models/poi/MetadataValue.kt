package com.example.androidchallenge.domain.models.poi


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class MetadataValue(
    @SerializedName("id")
    var id: Int?,
    @SerializedName("itemValue")
    var itemValue: String?,
    @SerializedName("metadataFieldID")
    var metadataFieldID: Int?,
    @SerializedName("metadataFieldOption")
    var metadataFieldOption: Any?,
    @SerializedName("metadataFieldOptionID")
    var metadataFieldOptionID: Any?
)