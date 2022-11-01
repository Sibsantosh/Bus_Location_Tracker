package com.rexmo.buslocationtracker.DriverLocation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewDebug.FlagToString
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityDriverDrivingBinding
import com.rexmo.buslocationtracker.permissions.LocationRequestPermissions
import firestore.FirestoreLocation
import models.DrivingData

class DriverDriving : AppCompatActivity() {
    var driving=false
    lateinit var locationRequest: LocationRequest
    lateinit var deviceLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var binding: ActivityDriverDrivingBinding
    val userPermission=LocationRequestPermissions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.
        setContentView(this,R.layout.activity_driver_driving)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.btnStartDriving.setOnClickListener {
            Toast.makeText(this,"$driving",Toast.LENGTH_SHORT).show()
            if (driving){
                //finish()
                binding.btnStartDriving.text="Start Driving"
                binding.txtShowLocation.text="Destination Reached"
                driving=false

            }
            else{
                getCurrentLocation()
                binding.btnStartDriving.text="Stop Driving"
                driving=true

            }

        }
    }

    /*val mHandler=Handler(Looper.getMainLooper())
    fun startRun(){
        //mHandler.postDelayed(mRunnable,200)
        mRunnable.run()
    }
    fun stopRun(){
        mHandler.removeCallbacks(mRunnable)
    }
    val mRunnable=Runnable(){
        fun run(){
        Toast.makeText(this,"Hii",Toast.LENGTH_SHORT).show()
            mHandler.postDelayed(startRun(),5000)
        }
    }*/









    private fun getCurrentLocation() {
        locationRequest=userPermission.locationRequest

        if (userPermission.checkPermissions(this))
        {
            if (userPermission.isLocationEnabled(this)) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    userPermission.requestPermission(this)
                    //return
                }
                else {

                    try {

                        fusedLocationProviderClient.requestLocationUpdates(
                            locationRequest,
                            object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    for (location in locationResult.locations) {
                                        deviceLocation=location
                                        if(driving) {
                                            binding.txtShowLocation.text = deviceLocation.toString()
                                            val sendLoc = DrivingData()
                                            sendLoc.latitude = deviceLocation.latitude.toString()
                                            sendLoc.longitude = deviceLocation.longitude.toString()
                                            sendLoc.location=location

//                                          val bear=location.bearing
                                            val Floca = FirestoreLocation()
                                            Floca.updateLocation(this, sendLoc)
                                        }
                                        else{
                                            return
                                            /*    val sendLoc = DrivingData()
                                            sendLoc.latitude = ""
                                            sendLoc.longitude = ""
                                            val Floca = FirestoreLocation()
                                            Floca.updateLocation(this, sendLoc)*/
                                        }


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
                        Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()



                        //onRestart()

                        // }
                    }
                }



            }
            else{
                Toast.makeText(this,"Turn on location",Toast.LENGTH_SHORT).show()
                userPermission.enableLocation(this)
            }

        }
        else {
            //request permission
            userPermission.requestPermission(this)

        }

    }


}