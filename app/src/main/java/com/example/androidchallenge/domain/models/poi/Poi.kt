package com.example.androidchallenge.domain.models.poi


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Poi(
    @SerializedName("addressInfo")
    var addressInfo: AddressInfo?,
    @SerializedName("connections")
    var connections: List<Connection?>?,
    @SerializedName("dataProvider")
    var dataProvider: Any?,
    @SerializedName("dataProviderID")
    var dataProviderID: Int?,
    @SerializedName("dataProvidersReference")
    var dataProvidersReference: String?,
    @SerializedName("dataQualityLevel")
    var dataQualityLevel: Int?,
    @SerializedName("dateCreated")
    var dateCreated: String?,
    @SerializedName("dateLastConfirmed")
    var dateLastConfirmed: String?,
    @SerializedName("dateLastStatusUpdate")
    var dateLastStatusUpdate: String?,
    @SerializedName("dateLastVerified")
    var dateLastVerified: String?,
    @SerializedName("datePlanned")
    var datePlanned: Any?,
    @SerializedName("generalComments")
    var generalComments: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("isRecentlyVerified")
    var isRecentlyVerified: Boolean?,
    @SerializedName("mediaItems")
    var mediaItems: Any?,
    @SerializedName("metadataValues")
    var metadataValues: List<MetadataValue?>?,
    @SerializedName("numberOfPoints")
    var numberOfPoints: Int?,
    @SerializedName("operatorID")
    var operatorID: Int?,
    @SerializedName("operatorInfo")
    var operatorInfo: Any?,
    @SerializedName("operatorsReference")
    var operatorsReference: String?,
    @SerializedName("parentChargePointID")
    var parentChargePointID: Any?,
    @SerializedName("percentageSimilarity")
    var percentageSimilarity: Any?,
    @SerializedName("statusType")
    var statusType: Any?,
    @SerializedName("statusTypeID")
    var statusTypeID: Int?,
    @SerializedName("submissionStatus")
    var submissionStatus: Any?,
    @SerializedName("submissionStatusTypeID")
    var submissionStatusTypeID: Int?,
    @SerializedName("usageCost")
    var usageCost: String?,
    @SerializedName("usageType")
    var usageType: Any?,
    @SerializedName("usageTypeID")
    var usageTypeID: Int?,
    @SerializedName("userComments")
    var userComments: Any?,
    @SerializedName("uuid")
    var uuid: String?
)
