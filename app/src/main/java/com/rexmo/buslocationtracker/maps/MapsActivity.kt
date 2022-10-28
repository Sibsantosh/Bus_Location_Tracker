package com.rexmo.buslocationtracker.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.R.menu.menu
import com.rexmo.buslocationtracker.permissions.LocationRequestPermissions

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
   //private lateinit var deviceLocation:Location
    private lateinit var deviceeLocation:Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        var sydney = LatLng(19.0755,83.8128)
        mMap.addMarker(MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }




    private fun getCurrentLocation() {
        val userPermission=LocationRequestPermissions()
        if (userPermission.checkPermissions(this))
        {
            if (userPermission.isLocationEnabled(this)) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    userPermission.requestPermission(this)
                    //return
                }
                else {

                    try {
                        val locationRequest=userPermission.locationRequest

                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    for (location in locationResult.locations) {

                                        //i.text="$location"
                                        deviceeLocation=location



                                    }
                                    // Few more things we can do here:
                                    // For example: Update the location of user on server
                                }
                            },
                            Looper.myLooper()
                        )
                    }
                    catch (e: Exception) {

                        //else {
                        ///open settings since location is not enabled
                        Toast.makeText(this, "Can't fetch location", Toast.LENGTH_SHORT).show()



                        //onRestart()

                        // }
                    }
                }



            }
            else{
                Toast.makeText(this,"Turn on location", Toast.LENGTH_SHORT).show()
                userPermission.enableLocation(this)
            }

        }
        else {
            //request permission
            userPermission.requestPermission(this)

        }

    }



    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        val inflater: MenuInflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }





}

