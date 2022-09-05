package com.adesso.casestudy.countries.model.remote

import com.google.gson.annotations.SerializedName

data class CountryDetails(
    @SerializedName("data")
    val details: CountryDetailsData
)
