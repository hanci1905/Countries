package com.adesso.casestudy.countries.model.remote

import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("code")
    val code: String?,
    @SerializedName("currencyCodes")
    val currencyCode: List<String?>,
    @SerializedName("name")
    val name: String?,
    @SerializedName("wikiDataId")
    val wikiDataID: String?
)
