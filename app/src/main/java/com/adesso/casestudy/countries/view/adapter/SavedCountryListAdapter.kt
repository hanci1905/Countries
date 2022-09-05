package com.adesso.casestudy.countries.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.adesso.casestudy.countries.R
import com.adesso.casestudy.countries.model.db.CountryRepository
import com.adesso.casestudy.countries.model.db.SavedCountryData
import com.adesso.casestudy.countries.view.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.item_country.view.*

class SavedCountryListAdapter(var context: Context, var countries: ArrayList<SavedCountryData>)
    : RecyclerView.Adapter<SavedCountryListAdapter.SavedCountryViewHolder>() {

    private lateinit var countryRepository: CountryRepository
    var selectedItem: String = ""

    inner class SavedCountryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageView = view.iv_star
        private val textView = view.tv_country

        fun bind(country: SavedCountryData){
            textView.text = country.name
            imageView.setImageResource(R.drawable.ic_star_black_24)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    SavedCountryViewHolder(LayoutInflater.from(parent.context)
    .inflate(R.layout.item_country, parent, false))

    override fun onBindViewHolder(holder: SavedCountryViewHolder, position: Int) {
        countryRepository = CountryRepository(context)
        holder.bind(countries[position])

        holder.itemView.tv_country.setOnClickListener { view ->
            selectedItem = countries[position].code
            view.findNavController().navigate(R.id.action_savedFragment_to_detailFragment2)
        }

        holder.itemView.iv_star.setOnClickListener {
            val savedData = countryRepository.getAllSavedCountries()

            for(data: SavedCountryData in savedData) {
                if(data.name.equals(countries[position].name)) {
                    holder.itemView.iv_star.setImageResource(R.drawable.ic_star_light_gray_24)
                    countryRepository.deleteCountry(data)
                    break
                }
            }

            updateCountries(ArrayList(countryRepository.getAllSavedCountries()))
        }
    }

    override fun getItemCount() = countries.size

    fun updateCountries(newCountries: ArrayList<SavedCountryData>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }
}