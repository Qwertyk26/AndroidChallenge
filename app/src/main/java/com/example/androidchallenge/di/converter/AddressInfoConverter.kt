package com.example.androidchallenge.di.converter

import com.example.androidchallenge.domain.models.poi.AddressInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class AddressInfoConverter: JsonDeserializer<AddressInfo> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): AddressInfo {
        val addressInfo = AddressInfo()
        addressInfo.title = json?.asJsonObject?.get("title")?.asString
        addressInfo.addressLine1 = json?.asJsonObject?.get("addressLine1")?.asString
        addressInfo.latitude = json?.asJsonObject?.get("latitude")?.asDouble
        addressInfo.longitude = json?.asJsonObject?.get("longitude")?.asDouble
        return addressInfo
    }
}