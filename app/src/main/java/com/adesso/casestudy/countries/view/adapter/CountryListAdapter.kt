package com.adesso.casestudy.countries.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adesso.casestudy.countries.R
import com.adesso.casestudy.countries.model.db.CountryRepository
import com.adesso.casestudy.countries.model.db.SavedCountryData
import com.adesso.casestudy.countries.model.remote.Country
import com.adesso.casestudy.countries.model.remote.CountryData
import kotlinx.android.synthetic.main.item_country.view.*
class CountryListAdapter(var context:Context, var countries: Country)
    : RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    private lateinit var countryRepository: CountryRepository
    var selectedItem: String = ""

    fun updateCountries(newCountries: Country) {
        countries.data.clear()
        countries.data.addAll(newCountries.data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false))

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        countryRepository = CountryRepository(context)
        holder.bind(countries.data[position])

        holder.itemView.iv_star.setOnClickListener {
            val savedData = countryRepository.getAllSavedCountries()
            var processed = false

            for(data: SavedCountryData in savedData) {
                if(data.name.equals(countries.data[position].name)) {
                    holder.itemView.iv_star.setImageResource(R.drawable.ic_star_light_gray_24)
                    countryRepository.deleteCountry(data)
                    processed = true
                    break
                }
            }

            if(!processed) {
                holder.itemView.iv_star.setImageResource(R.drawable.ic_star_black_24)
                countryRepository.insertCountry(
                    SavedCountryData(
                    countries.data[position].name.toString(),
                    countries.data[position].code.toString(),
                    countries.data[position].wikiDataID.toString())
                )
            }
        }

        holder.itemView.tv_country.setOnClickListener {  view ->
            selectedItem = countries.data[position].code.toString()
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment2)
        }
    }

    override fun getItemCount() = countries.data.size

     inner class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){
         private val imageView = view.iv_star
         private val textView = view.tv_country

         fun bind(country: CountryData){
             textView.text = country.name

             val data: SavedCountryData? = countryRepository.findByCountryName(country.name!!)

             if(data != null && data.name.isNotEmpty())
                 imageView.setImageResource(R.drawable.ic_star_black_24)
         }
     }
}