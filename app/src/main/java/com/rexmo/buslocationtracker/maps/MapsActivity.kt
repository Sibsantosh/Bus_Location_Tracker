package com.rexmo.buslocationtracker.maps


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.firestore.ReadData
import com.rexmo.buslocationtracker.permissions.LocationRequestPermissions
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var location: Location


    //private lateinit var fusedLocationProviderClient: FusedLocationProviderClient




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //getCurrentLocation()
        //readFirestoreData()
        //Handler(Looper.getMainLooper()).postDelayed({},3000)

        changeLocation()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
   override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val gunupur = LatLng(19.0493674,83.8324191)
        mMap.addMarker(MarkerOptions()
            .position(gunupur)
            .title("Marker in Gunupur"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gunupur,15f))

    }





    fun readFirestoreData() {
        val dataBase = FirebaseFirestore.getInstance().collection("Bus").document("23")
        dataBase.get().addOnSuccessListener {
            if (it.exists()) {
               location=it.get("location") as Location
                Toast.makeText(this,"hiii",Toast.LENGTH_SHORT).show()
//                s =
//                    it.getString("busNO") + it.getString("longitude") + it.getString("latitude") + "\n"
                //binding.txtShowLocation.text = s
              //Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()


            }
        }.addOnFailureListener {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()

        }

    }


    fun changeLocation(){

            //Handler(Looper.getMainLooper()).postDelayed({},2000)
        val handler=Handler(Looper.getMainLooper())
        val runnable=object: Runnable {

            override fun run() {
                readFirestoreData()
                handler.postDelayed(this,15000)
            }



        }
    handler.post(runnable)
    }
    }

