package com.rexmo.buslocationtracker.DriverLocation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityDriverDrivingBinding
import com.rexmo.buslocationtracker.databinding.ActivityMainBinding
import com.rexmo.buslocationtracker.permissions.LocationRequestPermissions
import com.skydoves.elasticviews.elasticAnimation
import firestore.FirestoreAttendance
import models.DrivingData

class DriverDriving : AppCompatActivity() {
    val driving=false
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
            if (driving==true){
                //finish()
                binding.btnStartDriving.text="Start Driving"
                binding.txtShowLocation.text="Destination Reached"
            }
            else{
                getCurrentLocation()
              /*  val drivingData=DrivingData()
                userAttendance.id= FirebaseAuth.getInstance().uid.toString()


                val fireStoreAttendance= FirestoreAttendance()
                fireStoreAttendance.putAttendance(this,userAttendance)*/
                binding.btnStartDriving.text="Stop Driving"
            }

        }
    }










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
                                        binding.txtShowLocation.text=deviceLocation.toString()



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