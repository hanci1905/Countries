package com.adesso.casestudy.countries.model.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryService {

    private val BASE_URL = "https://wft-geo-db.p.rapidapi.com"
    private val IMAGE_URL = "https://commons.wikimedia.org"

    fun getCountriesService(): CountriesApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)


    fun getCountryServiceForFlagImage(url: String): CountriesApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CountriesApi::class.java)
    }
}