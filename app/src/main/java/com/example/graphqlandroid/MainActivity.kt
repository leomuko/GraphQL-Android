package com.example.graphqlandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.graphqlandroid.databinding.ActivityMainBinding
import com.example.graphqlandroid.repository.Resource
import com.example.graphqlandroid.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            mainViewModel.getContinents().observe(this@MainActivity) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvContinents.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvContinents.visibility = View.VISIBLE
                        //Update UI according to the data received
                        binding.rvContinents.text = "Success"
                        Log.d(TAG, "setupObserver: "+ it.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvContinents.visibility = View.VISIBLE
                        //showToast(it.message.toString())
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Log.d(TAG, "setupObserver: Else - Called")
                    }
                }
            }

        }
    }
}