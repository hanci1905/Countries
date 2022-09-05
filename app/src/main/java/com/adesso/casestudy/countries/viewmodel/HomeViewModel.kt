package com.adesso.casestudy.countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adesso.casestudy.countries.model.remote.Country
import com.adesso.casestudy.countries.model.remote.CountryService
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    private val countriesService = CountryService.getCountriesService()

    var job: Job? = null
    val country = MutableLiveData<Country>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {

        loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = countriesService.getCountries(10)

            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                    loading.value = false
                    country.value = response.body()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}