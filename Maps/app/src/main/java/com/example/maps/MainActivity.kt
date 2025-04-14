package com.example.maps

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment

        mapFragment?.getMapAsync(this)


    }


    override fun onMapReady(gmap: GoogleMap) {
        gMap = gmap
        val latLng = LatLng(37.4219999, -122.0862462)
        gMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15F))
    }
}