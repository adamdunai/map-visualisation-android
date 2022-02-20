package com.example.mapvisualisation.main

import android.Manifest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mapvisualisation.R
import com.example.mapvisualisation.databinding.ActivityMainBinding
import com.example.mapvisualisation.main.model.ScooterClusterItem
import com.example.mapvisualisation.main.model.ScooterMapUiState
import com.example.mapvisualisation.main.viewmodel.ScooterMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@AndroidEntryPoint
@RuntimePermissions
class MainActivity : AppCompatActivity() {

    private lateinit var activityBinding: ActivityMainBinding
    private lateinit var clusterManager: ClusterManager<ScooterClusterItem>

    private val viewModel: ScooterMapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)

        initMap()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collectLatest { state ->
                    when (state) {
                        ScooterMapUiState.Error -> {
                            activityBinding.progressBar.isVisible = false
                            showErrorSnackbar(R.string.scooter_map_error)
                        }
                        ScooterMapUiState.Loading -> {
                            activityBinding.progressBar.isVisible = true
                        }
                        ScooterMapUiState.NetworkError -> {
                            activityBinding.progressBar.isVisible = false
                            showErrorSnackbar(R.string.scooter_map_network_error)
                        }
                        ScooterMapUiState.Success -> {
                            activityBinding.progressBar.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun initMap() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.mapFragmentContainerView) as SupportMapFragment
                val googleMap = mapFragment.awaitMap()
                googleMap.awaitMapLoad()

                if (!this@MainActivity::clusterManager.isInitialized) {
                    initClusterManager(googleMap)
                }

                moveCameraToCurrentLocationWithPermissionCheck(googleMap)

                viewModel.scooterListFlow.collectLatest { clusterList ->
                    addClusters(clusterList)
                }
            }
        }
    }

    private fun initClusterManager(googleMap: GoogleMap) {
        clusterManager = ClusterManager<ScooterClusterItem>(
            this@MainActivity,
            googleMap
        ).apply {
            setOnClusterItemClickListener { item ->
                ScooterInfoDialogFragment().showBottomSheet(
                    manager = supportFragmentManager,
                    scooterId = item.id
                )
                true
            }
        }
        googleMap.setOnCameraIdleListener(clusterManager)
    }

    private fun addClusters(clusterList: List<ScooterClusterItem>) {
        clusterManager.apply {
            clearItems()
            addItems(clusterList)
            cluster()
        }
    }

    private fun showErrorSnackbar(@StringRes messageResId: Int) {
        Snackbar.make(
            activityBinding.root,
            messageResId,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.scooter_map_reload) { viewModel.fetchScooterList() }
            .show()
    }

    @NeedsPermission(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun moveCameraToCurrentLocation(map: GoogleMap) {
        lifecycleScope.launch {
            viewModel.locationFlow
                .collectLatest { location ->
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(location.latitude, location.longitude),
                            MAP_CAMERA_ZOOM_LEVEL
                        )
                    )
                }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode, grantResults)
    }

    @OnPermissionDenied(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    @OnNeverAskAgain(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    fun onLocationPermissionDenied() {
        Snackbar.make(
            activityBinding.root,
            R.string.scooter_map_location_permission_denied,
            Snackbar.LENGTH_LONG
        ).show()
    }

    companion object {
        const val MAP_CAMERA_ZOOM_LEVEL = 10f
    }
}
