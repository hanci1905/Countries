package com.adesso.casestudy.countries.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.adesso.casestudy.countries.R
import com.adesso.casestudy.countries.databinding.ActivityMainBinding
import com.adesso.casestudy.countries.model.remote.CountryService
import com.adesso.casestudy.countries.utils.NavigationCallback
import com.adesso.casestudy.countries.viewmodel.DetailViewModel
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MainActivity : AppCompatActivity(), NavigationCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = DetailViewModel.getInstance()
        viewModel.navCallback = this

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener {
            val fragmentInstance = navController.currentDestination?.id

            when(it.itemId) {
                R.id.navigation_home -> {
                    if(fragmentInstance == R.id.savedFragment)
                        navController.navigate(R.id.action_savedFragment_to_homeFragment)
                }
                R.id.navigation_saved -> {
                    if(fragmentInstance == R.id.homeFragment)
                        navController.navigate(R.id.action_homeFragment_to_savedFragment)
                }
            }

            true
        }

    }

    override fun onDetailFragmentVisible() {
        if(binding.navView.visibility == View.VISIBLE)
            binding.navView.visibility = View.GONE
        else
            binding.navView.visibility = View.VISIBLE
    }
}