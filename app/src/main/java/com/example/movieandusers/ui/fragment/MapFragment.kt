package com.example.movieandusers.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieandusers.BuildConfig
import com.example.movieandusers.R
import com.example.movieandusers.ui.REQUEST_CODE_LOCATION
import com.example.movieandusers.databinding.FragmentMapBinding
import com.example.movieandusers.ext.checkPermission
import com.example.movieandusers.ext.isPermissionGranted
import com.example.movieandusers.ext.requestOnePermission
import com.example.movieandusers.ext.toast
import com.example.movieandusers.service.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener  {
    private var mMap: GoogleMap? = null
    private var origin: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // validate permission for location or request permission
        if (requireActivity().checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initFusedLocation()
            initMap()
            initLocationService()
        } else
            requestOnePermission(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_CODE_LOCATION)
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(requireContext().applicationContext, BuildConfig.MAPS_API_KEY)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        mMap = map
        mMap?.isMyLocationEnabled = true
        mMap?.setOnMyLocationButtonClickListener(this)
        mMap?.setOnMyLocationClickListener(this)

    }

    @SuppressLint("MissingPermission")
    private fun initFusedLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                updateMapLocation(location)
            }
    }

    private fun initLocationService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireContext().startForegroundService(Intent(context, LocationService::class.java))
        } else {
            requireContext().startService(Intent(context, LocationService::class.java))
        }
    }

    private fun updateMapLocation(location: Location?) {
        origin = LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)

        location?.let {
            mMap?.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                location.latitude,
                location.longitude
            )
                ))

            mMap?.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
        }
    }

    override fun onMyLocationClick(location: Location) {
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE_LOCATION) {
            return
        }
        if (requireActivity().isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            initFusedLocation()
            initMap()
            initLocationService()
        } else {
            requireContext().toast("Permission was denied")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment()
    }


}