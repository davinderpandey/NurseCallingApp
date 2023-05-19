package com.app.nursecalling.activities

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract.EventsEntity
import android.widget.Button
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity

import com.app.nursecalling.R
import com.app.nursecalling.commoners.AppUtils
import com.app.nursecalling.model.UserModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var btnSendData : Button
    private lateinit var editUserName : EditText
    private lateinit var editPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        btnSendData = findViewById(R.id.btn_Login)
        editUserName = findViewById(R.id.et_channelId)
        editPassword = findViewById(R.id.et_password)

        btnSendData.setOnClickListener {
            startActivity(Intent(this,EventsActivity::class.java)
                .putExtra("username",editUserName.text.toString())
                .putExtra("password",editPassword.text.toString()))
        }
        setInitialUser()


    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Nurse Calling App"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
    override fun onResume() {
        super.onResume()
        bottom_nav.selectedItemId = R.id.home
    }
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.profile -> {
                    startActivity(Intent(this, UserProfileActivity::class.java))
                    AppUtils.animateEnterRight(this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.events -> {
                    startActivity(Intent(this, EventsActivity::class.java).putExtra("heading","HealthCare"))
                    AppUtils.animateEnterRight(this@MainActivity)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun setInitialUser(){
        val userName = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val currentUser = UserModel();
        currentUser.channelId = userName;
        currentUser.Password = password;
        currentUser.save()
    }


}