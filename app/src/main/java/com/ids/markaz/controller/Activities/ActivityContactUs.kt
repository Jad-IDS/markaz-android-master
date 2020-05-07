package com.ids.markaz.controller.Activities


import android.os.Bundle

import kotlinx.android.synthetic.main.toolbar.*

import com.google.android.gms.maps.GoogleMap
import com.ids.markaz.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng



import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.activity_contact.*
import com.ids.markaz.controller.Base.ActivityBase

import com.google.android.gms.maps.model.MarkerOptions
import android.content.Intent
import android.net.Uri




class ActivityContactUs : ActivityBase(), OnMapReadyCallback {

    private var gmap: GoogleMap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(getString(R.string.google_maps_key))
        }
        mapView.onCreate(mapViewBundle);
        init()
    }

    private fun init(){
        tvToolbarTitle.text=getString(R.string.contact_us)
        btBack.setOnClickListener{
            super.onBackPressed()
        }

        linearCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+tvCall.text.toString())
            startActivity(intent)
        }

        linearEmail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:"+tvEmail.text.toString())
            startActivity(Intent.createChooser(emailIntent, "Send feedback"))
        }




        mapView.getMapAsync(this);
    }


    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        var mapViewBundle = outState.getBundle(getString(R.string.google_maps_key))
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(getString(R.string.google_maps_key), mapViewBundle)
        }

        mapView!!.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onPause() {
        mapView!!.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView!!.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

   override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap!!.setMinZoomPreference(12f)
        val ny = LatLng(33.8658486, 35.5483189)
        gmap!!.moveCamera(CameraUpdateFactory.newLatLng(ny))
        val markerOptions = MarkerOptions()
        markerOptions.position(ny)
        gmap!!.addMarker(markerOptions)
    }

}
