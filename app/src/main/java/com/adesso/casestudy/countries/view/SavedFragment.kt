package com.adesso.casestudy.countries.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adesso.casestudy.countries.databinding.FragmentSavedBinding
import com.adesso.casestudy.countries.model.db.CountryRepository
import com.adesso.casestudy.countries.model.remote.Country
import com.adesso.casestudy.countries.view.adapter.CountryListAdapter
import com.adesso.casestudy.countries.view.adapter.SavedCountryListAdapter
import com.adesso.casestudy.countries.viewmodel.DetailViewModel
import com.adesso.casestudy.countries.viewmodel.SavedViewModel

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: SavedViewModel
    private lateinit var detailViewModel: DetailViewModel
    private  lateinit var countriesAdapter: SavedCountryListAdapter
    private lateinit var databaseRepository: CountryRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this).get(SavedViewModel::class.java)
        viewModel.refresh()

        binding = FragmentSavedBinding.inflate(inflater, container, false)
        detailViewModel = DetailViewModel.getInstance()

        databaseRepository = CountryRepository(requireContext())

        countriesAdapter = SavedCountryListAdapter(requireContext(), ArrayList(databaseRepository.getAllSavedCountries()))

        binding.rvCountriesSaved.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        return binding.root
    }

    override fun onPause() {
        detailViewModel.selectedItem.value = countriesAdapter.selectedItem
        super.onPause()
    }
}