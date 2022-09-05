package com.adesso.casestudy.countries.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_data")
data class SavedCountryData(
    var name: String,
    var code: String,
    var wikiDataID: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
