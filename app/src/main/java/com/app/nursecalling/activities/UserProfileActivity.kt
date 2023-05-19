package com.app.nursecalling.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.app.nursecalling.R
import com.app.nursecalling.model.UserModel
import com.bumptech.glide.Glide

class UserProfileActivity : AppCompatActivity() {

    private lateinit var usernameTextView: TextView
    private lateinit var passwordTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Get the views from the layout
        usernameTextView = findViewById(R.id.tv_username)
        passwordTextView = findViewById(R.id.tv_password)


        val user = getUser();
        println("current User -> "+ user)
        usernameTextView.text = user.channelId
        passwordTextView.text = user.Password


    }
   private fun getUser(): UserModel {
        val listAll = UserModel.listAll(UserModel::class.java)
        var currentUser=UserModel();
       if(listAll.isNotEmpty() && listAll.size >1){
           currentUser = listAll[listAll.size-1];
       }else
           currentUser = listAll.get(0)
       return currentUser;
    }
}