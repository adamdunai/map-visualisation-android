package com.example.mapvisualisation.main.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ScooterClusterItem(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val itemTitle: String? = null,
    val itemSnippet: String? = null,
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
    override fun getTitle(): String? = itemTitle
    override fun getSnippet(): String? = itemSnippet
}
