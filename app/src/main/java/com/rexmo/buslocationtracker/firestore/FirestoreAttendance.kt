package firestore

import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.rexmo.buslocationtracker.DriverLocation.DriverDriving
import models.DrivingData


class FirestoreAttendance {
   private val mFirestore= FirebaseFirestore.getInstance()

    fun putAttendance(activity: DriverDriving, userInfo: DrivingData){

        //the user is a collection if it dont exist then it will create one
        mFirestore.collection("Bus")
            //the document id for user and here the document is userid
            .document(userInfo.busNO)
            //here the user info is fields to be pushed
            .set(userInfo, SetOptions.merge())
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