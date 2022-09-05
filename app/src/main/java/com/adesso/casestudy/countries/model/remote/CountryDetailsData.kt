package com.adesso.casestudy.countries.model.remote

import com.google.gson.annotations.SerializedName

data class CountryDetailsData(
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("code")
    val code: String?,
    @SerializedName("callingCode")
    val callingCode: String?,
    @SerializedName("currencyCodes")
    val currencyCodes: List<String?>,
    @SerializedName("flagImageUri")
    val imageUri: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("numRegions")
    val regionTotals: String?,
    @SerializedName("wikiDataId")
    val id: String?
)
