package com.rexmo.buslocationtracker.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*



class LocationRequestPermissions {
    lateinit var myActivity: Activity



    var locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 200)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(200)
            .setMaxUpdateDelayMillis(200)
            .build()


    fun requestPermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.CAMERA
            ),
            PERMISSION
        )
        // getCurrentLocation()
    }

    companion object {
        private const val PERMISSION = 100
    }


    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )

    }

    fun enableLocation(context: Context){
        val i=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(i)
        myActivity=context as Activity
        myActivity.finish()


    }

    fun checkPermissions(context: Context): Boolean {
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }





/*
     fun getCurrentLocation(context: Context):Unit {

        if (checkPermissions(context)) {
            if (isLocationEnabled(context)) {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermission(context)
                } else {

                    try {

                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    for (location in locationResult.locations) {
                                            deviceLocation=location
                                    }
                                }
                            },
                            Looper.myLooper()
                        )
                    } catch (e: Exception) {

                        //else {
                        ///open settings since location is not enabled
                        Toast.makeText(context, "cant fetch location", Toast.LENGTH_SHORT).show()

                    }
                }


            } else {
                Toast.makeText(context, "Turn on location", Toast.LENGTH_SHORT).show()
                val i = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(i)


            }

        } else {
            //request permission
            requestPermission(context)

        }
    }*/

}