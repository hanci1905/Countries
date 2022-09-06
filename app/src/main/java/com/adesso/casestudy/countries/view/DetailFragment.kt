package com.adesso.casestudy.countries.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.adesso.casestudy.countries.databinding.FragmentDetailBinding
import com.adesso.casestudy.countries.model.remote.CountryService
import com.adesso.casestudy.countries.viewmodel.DetailViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.*


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var wikiUri = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = DetailViewModel.getInstance()
        viewModel.getDetail(viewModel.selectedItem.value!!)

        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.tvCountryCode.text = viewModel.selectedItem.value

        viewModel.country.observe(viewLifecycleOwner) { countryDetail ->

            val uri = Uri.parse(countryDetail.details.imageUri)

            //Log.e("TAG", "Uri: $uri")
            //Utils().fetchSVG(requireContext(),
             //   "https://commons.wikimedia.org/wiki/Special:FilePath/Flag%20of%20Kazakhstan.svg", binding.ivCountryFlag)

            val serv = CountryService.getCountryServiceForFlagImage(
                "http://commons.wikimedia.org")

            Log.e("TAG", "Url: " + countryDetail.details.imageUri + " ---: " + uri)
            CoroutineScope(Dispatchers.IO).launch {
                val stream = serv.getFlagDataFromSvg("/wiki/Special:FilePath/Flag%20of%20the%20United%20States.svg")

                Log.e("TAG", "" + stream.code() + " " + stream.isSuccessful)

                withContext(Dispatchers.Main) {
                    Glide
                        .with(requireActivity())
                        .load(stream.body())
                        .into(binding.ivCountryFlag)
                }
            }

            binding.btMoreInfo.setOnClickListener {
                wikiUri = "https://www.wikidata.org/wiki/" + countryDetail.details.id

                val uri = Uri.parse(wikiUri)
                val intent = Intent(Intent.ACTION_VIEW, uri)

                startActivity(intent)
            }
        }

        viewModel.navCallback.onDetailFragmentVisible()
        return binding.root
    }

    override fun onPause() {
        viewModel.navCallback.onDetailFragmentVisible()
        super.onPause()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.navCallback.onDetailFragmentVisible()
        super.onSaveInstanceState(outState)
    }
}