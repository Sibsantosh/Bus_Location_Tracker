package firestore

import com.google.android.gms.location.LocationCallback
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import models.DrivingData


class FirestoreLocation {
   private val mFirestore= FirebaseFirestore.getInstance()

    fun updateLocation(activity: LocationCallback, busLocation: DrivingData){

        //the user is a collection if it dont exist then it will create one
        mFirestore.collection("Bus")
            //the document id for user and here the document is userid
            .document(busLocation.busNO)
            //here the user info is fields to be pushed
            .set(busLocation, SetOptions.merge())
            .addOnSuccessListener {
                /*activity.success="Attendance Updated"
                activity.attendanceUpdated()*/
            }.addOnFailureListener{
               /* activity.stopProgress()
                activity.success="$it"*/
               // Toast.makeText(activity,"$it", Toast.LENGTH_SHORT).show()
            }
    }


}