package com.adesso.casestudy.countries.model.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.adesso.casestudy.countries.model.remote.CountryData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait

class CountryRepository(context: Context) {

    private val database = CountryDatabase(context)
    private val dao = database.countryDao()

    fun insertCountry(countryName: SavedCountryData) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(countryName)
        }
    }

    fun deleteCountry(countryName: SavedCountryData) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.delete(countryName)
        }
    }

    fun getAllSavedCountries(): List<SavedCountryData> {
        var data: List<SavedCountryData>

      runBlocking {
          data = dao.getAllCountries()
      }

        return data
    }

    fun findByCountryName(name: String): SavedCountryData {
        var data: SavedCountryData

        runBlocking {
            data = dao.findByCountryName(name)
        }

        return data
    }
}