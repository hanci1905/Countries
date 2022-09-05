package com.adesso.casestudy.countries.model.remote


import com.adesso.casestudy.countries.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.InputStream

interface CountriesApi {

    @Headers("X-RapidAPI-Key: ${BuildConfig.API_KEY}",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com")
    @GET("v1/geo/countries")
    suspend fun getCountries(
        @Query("limit") limit: Int = 10
    ): Response<Country>

    @Headers("X-RapidAPI-Key: ${BuildConfig.API_KEY}",
        "X-RapidAPI-Host: wft-geo-db.p.rapidapi.com")
    @GET("/v1/geo/countries/{code}")
    suspend fun getCountriesDetails(@Path("code") code: String): Response<CountryDetails>

    @GET("/{code}")
    suspend fun getFlagDataFromSvg(@Path("code") code: String): Response<InputStream>

}