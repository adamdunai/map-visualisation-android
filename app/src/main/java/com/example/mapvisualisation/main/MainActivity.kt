package com.example.mapvisualisation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mapvisualisation.R
import com.example.mapvisualisation.databinding.ActivityMainBinding
import com.example.mapvisualisation.main.model.ScooterClusterItem
import com.google.android.gms.maps.SupportMapFragment
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var clusterManager: ClusterManager<ScooterClusterItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        initMap()
    }

    private fun initMap() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.mapFragmentContainerView) as SupportMapFragment
                val googleMap = mapFragment.awaitMap()
                googleMap.awaitMapLoad()

                if (!this@MainActivity::clusterManager.isInitialized) {
                    clusterManager = ClusterManager<ScooterClusterItem>(
                        this@MainActivity,
                        googleMap
                    )
                    googleMap.setOnCameraIdleListener(clusterManager)
                }
            }
        }
    }
}
