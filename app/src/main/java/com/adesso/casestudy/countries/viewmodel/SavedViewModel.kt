package com.adesso.casestudy.countries.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adesso.casestudy.countries.model.remote.CountryData

class SavedViewModel : ViewModel() {

     var country = MutableLiveData<List<CountryData>>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {

    }
}