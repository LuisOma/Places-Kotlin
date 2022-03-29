package com.example.minutestest.ui.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.minutestest.R
import com.example.minutestest.domain.model.Result
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private var mainViewModel: MainViewModel? = null
    private lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel =
            activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        mainViewModel?.selectedLocation?.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                setLocation(it.geometry.location.lat,it.geometry.location.lng, it.name)
            }
        })

        mainViewModel?.currentLocation?.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                val lat = it.substringBefore(",").toDouble()
                val lon = it.substringAfter(",").toDouble()
                setDefaultLocation(lat, lon)
            }
        })


        mainViewModel?.nearLocations?.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                setNearLocations(it)
            }
        })

    }

    fun setLocation(lat: Double, lon: Double, name: String){
        val location = LatLng(lat, lon)
        googleMap.addMarker(MarkerOptions().position(location).title(name))?.showInfoWindow()
        val zoomLevel = 14.0f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
        googleMap.setOnInfoWindowClickListener {_marker ->
            _marker.title
            mainViewModel?.nearLocations?.value?.forEach {
                if(it.name.equals(_marker.title)){
                    mainViewModel?.detailLocation?.value = it
                }
            }
        }
    }

    fun setDefaultLocation(lat: Double, lon: Double){
        val location = LatLng(lat, lon)
        val zoomLevel = 14.0f
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel))
    }

    fun setNearLocations(list: List<Result>){
        list.forEach {
            val location = LatLng(it.geometry.location.lat, it.geometry.location.lng)
            googleMap.addMarker(MarkerOptions().position(location).title(it.name))
        }
    }
}