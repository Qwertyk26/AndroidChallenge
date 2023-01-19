package com.example.androidchallenge.domain.models.poi


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Connection(
    @SerializedName("amps")
    var amps: Int?,
    @SerializedName("comments")
    var comments: Any?,
    @SerializedName("connectionType")
    var connectionType: Any?,
    @SerializedName("connectionTypeID")
    var connectionTypeID: Int?,
    @SerializedName("currentType")
    var currentType: Any?,
    @SerializedName("currentTypeID")
    var currentTypeID: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("level")
    var level: Any?,
    @SerializedName("levelID")
    var levelID: Int?,
    @SerializedName("powerKW")
    var powerKW: Double?,
    @SerializedName("quantity")
    var quantity: Int?,
    @SerializedName("reference")
    var reference: Any?,
    @SerializedName("statusType")
    var statusType: Any?,
    @SerializedName("statusTypeID")
    var statusTypeID: Int?,
    @SerializedName("voltage")
    var voltage: Int?
)