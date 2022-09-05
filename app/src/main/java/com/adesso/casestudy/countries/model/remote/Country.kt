package com.adesso.casestudy.countries.model.remote

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("data")
    val data: ArrayList<CountryData>
)