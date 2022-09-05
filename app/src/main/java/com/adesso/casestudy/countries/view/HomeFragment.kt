package com.adesso.casestudy.countries.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.adesso.casestudy.countries.R
import com.adesso.casestudy.countries.databinding.FragmentHomeBinding
import com.adesso.casestudy.countries.model.remote.Country
import com.adesso.casestudy.countries.view.adapter.CountryListAdapter
import com.adesso.casestudy.countries.viewmodel.DetailViewModel
import com.adesso.casestudy.countries.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_country.view.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var detailViewModel: DetailViewModel
    private  lateinit var countriesAdapter: CountryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.refresh()

        detailViewModel = DetailViewModel.getInstance()

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        countriesAdapter = CountryListAdapter(requireContext(), Country(arrayListOf()))

        binding.rvCountries.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.country.observe(viewLifecycleOwner) { countries ->
            countries?.let {
                countriesAdapter.updateCountries(it)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if(loading) {
                binding.pbLoading.visibility = View.VISIBLE
                binding.rvCountries.visibility = View.GONE
                binding.textTitle.visibility = View.GONE
            } else {
                binding.pbLoading.visibility = View.GONE
                binding.rvCountries.visibility = View.VISIBLE
                binding.textTitle.visibility = View.VISIBLE
            }
        }
    }

    override fun onPause() {
        detailViewModel.selectedItem.value = countriesAdapter.selectedItem
        super.onPause()
    }
}