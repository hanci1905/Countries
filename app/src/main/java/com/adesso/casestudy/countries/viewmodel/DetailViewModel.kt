package com.adesso.casestudy.countries.viewmodel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adesso.casestudy.countries.model.remote.CountryDetails
import com.adesso.casestudy.countries.model.remote.CountryService
import com.adesso.casestudy.countries.utils.NavigationCallback
import kotlinx.coroutines.*
import java.io.InputStream

class DetailViewModel: ViewModel() {

    private val countriesService = CountryService.getCountriesService()

    var job: Job? = null
    val country = MutableLiveData<CountryDetails>()
    val stream = MutableLiveData<InputStream>()
    val selectedItem = MutableLiveData<String>()
    lateinit var navCallback: NavigationCallback

    companion object{
        private lateinit var instance: DetailViewModel

        @MainThread
        fun getInstance(): DetailViewModel{
            instance = if(::instance.isInitialized) instance else DetailViewModel()
            return instance
        }
    }

    fun getDetail(countryCode: String) {
        fetchCountries(countryCode)
    }

    fun getFlag(countryFlagUri: String) {
        getCountryFlag(countryFlagUri)
    }

    private fun fetchCountries(countryCode: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = countriesService.getCountriesDetails(countryCode)

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    country.value = response.body()
                }
            }
        }
    }

    private fun getCountryFlag(url: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = countriesService.getFlagDataFromSvg(url)

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    stream.value = response.body()
                }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}