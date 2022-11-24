package com.rexmo.buslocationtracker.Login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.rexmo.buslocationtracker.MainActivity
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityRegisterBinding
import firestore.FirestoreClass
import models.User

class RegisterActivity : AppCompatActivity() {


    private lateinit var email:String
    private lateinit var busNo:String
    private lateinit var Numberplate:String
    private lateinit var driverName:String
    private lateinit var driverNo:String
    private lateinit var password:String
    private lateinit var confirmPassword:String
    private lateinit var txtLogin: TextView
    private lateinit var mProgressbar: Dialog
    val user= User()
    lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.
        setContentView(this,R.layout.activity_register)



        binding.btnRegister.setOnClickListener {
            if (binding.etEmail.text.isEmpty() || binding.etPassword.text.isEmpty() || binding.etPasswordConfirm.text.isEmpty()) {
                Toast.makeText(this, "Enter details", Toast.LENGTH_SHORT).show()
            }
            else {
                email = binding.etEmail.text.toString().trim {it <=' '}
                password = binding.etPassword.text.toString().trim {it <=' '}
                 driverNo= binding.etDriverNumber.text.toString().trim {it <=' '}
                 busNo= binding.etDriverNumber.text.toString().trim {it <=' '}
                driverName = binding.etName.text.toString().trim {it <=' '}
                confirmPassword = binding.etPasswordConfirm.text.toString().trim()
                if (password != confirmPassword) {
                    Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show()
                }
                else {
                    showProgress()
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener{ task->
                                if(task.isSuccessful){
                                    val firebaseUser: FirebaseUser =task.result!!.user!!
                                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                                    user.driverID=firebaseUser.uid
                                    user.email=email
                                    user.driverNo=driverNo
                                    user.busNumber=busNo
                                    user.driverName=driverName
                                    FirestoreClass().registerUser(this,user)
                                    FirebaseAuth.getInstance().signOut()
                                    val iLogin= Intent(this, ActivityLogin::class.java)
                                    startActivity(iLogin)
                                }
                                else
                                {
                                    Toast.makeText(this,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                    stopProgress()
                                }
                            }
                }
            }


        }

        binding.txtLogin.setOnClickListener{
            val iLogin= Intent(this, ActivityLogin::class.java)
            startActivity(iLogin)
        }

    }








    fun showProgress()
    {
        mProgressbar= Dialog(this)
        mProgressbar.setContentView(R.layout.layout_progress_bar)
        mProgressbar.setCancelable(false)
        mProgressbar.setCanceledOnTouchOutside(false)
        mProgressbar.show()
    }
    fun stopProgress(){
        mProgressbar.dismiss()
    }



    fun completedRegistration(){
        stopProgress()
        Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show()
    }
}