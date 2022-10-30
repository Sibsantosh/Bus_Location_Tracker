package com.rexmo.buslocationtracker.Login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.rexmo.buslocationtracker.DriverLocation.DriverDriving
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var fEmail: String
    private lateinit var password: String
    private lateinit var mProgressBar: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        binding.btnLogin.setOnClickListener {

            if(binding.etEmail.text.isEmpty()&&binding.etPassword.text.isEmpty()){
                Toast.makeText(this,"Enter email and password",Toast.LENGTH_SHORT).show()
            }
            else{
                showProgressBar()
                email=binding.etEmail.text.toString().trim(){it <=' '}
                password=binding.etPassword.text.toString().trim(){it <=' '}
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(
                        OnCompleteListener <AuthResult>{
                                task->
                            stopProgress()
                            if(task.isSuccessful){
                                //val firebaseUser: FirebaseUser =task.result!!.user!!
                                Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show()
                                val iLogin= Intent(this, DriverDriving::class.java)
                                startActivity(iLogin)
                            }
                            else
                            {
                                Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    )

        }
    }

        binding.txtNewRegister.setOnClickListener {
            val i=Intent(this,RegisterActivity::class.java)
            startActivity(i)
        }











    }
    fun showProgressBar(){
        mProgressBar=Dialog(this)
        mProgressBar.setContentView(R.layout.layout_progress_bar)
        mProgressBar.setCancelable(false)
        mProgressBar.setCanceledOnTouchOutside(false)
        mProgressBar.show()
    }
    fun stopProgress()
    {
        mProgressBar.dismiss()
    }
}