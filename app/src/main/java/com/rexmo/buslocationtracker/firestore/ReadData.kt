package com.rexmo.buslocationtracker.firestore

import android.app.Activity
import android.location.Location
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class ReadData {
    lateinit var location: Location
    lateinit var s:String
    fun readFirestoreData(activity:Activity){
        val dataBase= FirebaseFirestore.getInstance().collection("Bus").document("23")
        dataBase.get().addOnSuccessListener {
            if (it.exists()){
                location.latitude=it.getString("latitude").toString().toDouble()
                location.longitude=it.getString("longitude").toString().toDouble()
                s=it.getString("busNO")+it.getString("longitude")+it.getString("latitude")+"\n"

            }
        }.addOnFailureListener {
            Toast.makeText(activity,"$it", Toast.LENGTH_SHORT).show()
        }

    }
}