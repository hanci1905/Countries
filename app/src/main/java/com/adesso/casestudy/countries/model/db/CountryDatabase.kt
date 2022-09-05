package com.adesso.casestudy.countries.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adesso.casestudy.countries.model.remote.CountryData

@Database(entities = [SavedCountryData::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile private var instance: CountryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "country_database"
        ).build()
    }
}