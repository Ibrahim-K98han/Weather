package com.ibrahim.weather

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.Debug.getLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.ibrahim.weather.viewmodels.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private val weatherViewModel: WeatherViewModel by viewModels()

    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                detectUserLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                detectUserLocation()
            } else -> {
            //Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
        }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun detectUserLocation() {
        getLocation(this) {
            weatherViewModel.setNewLocation(it)
        }
    }
}