package firestore
import android.app.Activity
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.rexmo.buslocationtracker.Login.RegisterActivity

import models.User

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {

        //the user is a collection if it dont exist then it will create one
        mFirestore.collection("Driver")

            //the document id for user and here the document is userid
            .document(userInfo.driverID)
            //here the user info is fields to be pushed
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.completedRegistration()
            }.addOnFailureListener {
                activity.stopProgress()
                //Toast.makeText(activity, "$it", Toast.LENGTH_SHORT).show()
            }
    }
}