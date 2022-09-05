package com.adesso.casestudy.countries.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adesso.casestudy.countries.model.remote.Country
import com.adesso.casestudy.countries.model.remote.CountryData

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(country: SavedCountryData)

    @Delete
    suspend fun delete(country: SavedCountryData)

    @Query("SELECT * FROM saved_data")
    suspend fun getAllCountries(): List<SavedCountryData>

    @Query("SELECT * FROM saved_data WHERE name = :name")
    suspend fun findByCountryName(name: String): SavedCountryData
}