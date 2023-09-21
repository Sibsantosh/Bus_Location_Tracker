package com.rexmo.buslocationtracker.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityBusNumberBinding

class ActivityBusNumber : AppCompatActivity() {
    lateinit var binding:ActivityBusNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.
        setContentView(this,R.layout.activity_bus_number)
        binding.btnShowLocation.setOnClickListener {
            var bus=binding.etBusNumber.text.toString().trim { it<=' '}
            if(bus==""){
                Toast.makeText(this,"Enter Bus Number",Toast.LENGTH_SHORT).show()
            }
            else {
                val i = Intent(this, MapsActivity::class.java)

                //i.putExtra("busNumber", bus)
                //Toast.makeText(this,bus,Toast.LENGTH_SHORT).show()
                startActivity(i)
            }
        }

    }
}