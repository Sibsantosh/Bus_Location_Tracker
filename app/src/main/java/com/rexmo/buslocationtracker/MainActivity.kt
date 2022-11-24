package com.rexmo.buslocationtracker


import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import com.rexmo.buslocationtracker.Login.ActivityLogin
import com.rexmo.buslocationtracker.databinding.ActivityMainBinding
import com.rexmo.buslocationtracker.maps.ActivityBusNumber
import com.rexmo.buslocationtracker.maps.MapsActivity

open class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    //@SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        binding.imgLoginAsDriver.setOnClickListener {
            val i = Intent(this, ActivityLogin::class.java)
            startActivity(i)
        }
        binding.imgViewLocation.setOnClickListener {
            val i=Intent(this,ActivityBusNumber::class.java)
            startActivity(i)
        }



    }



    
}

