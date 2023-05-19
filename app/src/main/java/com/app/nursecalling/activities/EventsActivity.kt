package com.app.nursecalling.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.nursecalling.R
import com.app.nursecalling.adapters.EventsAdapter
import com.app.nursecalling.commoners.AppUtils
import com.app.nursecalling.model.EventsModel
import com.app.nursecalling.model.UserModel
import com.app.nursecalling.service.ApiService
import com.app.nursecalling.service.RetrofitInstance
import com.app.nursecalling.utils.PreferenceHelper
import com.orm.SugarDb
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EventsActivity : AppCompatActivity() {


    private lateinit var prefs: SharedPreferences
    private lateinit var eventTime: Instant
    private lateinit var elapsed_time_textview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)
        setSupportActionBar(toolbar)
        setUser();
        prefs = PreferenceHelper.defaultPrefs(this)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Events"
        val eventsList= ArrayList<EventsModel>()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        eventsList.add(EventsModel(1,"CALL","ROOM","User", LocalDateTime.now()))
        eventsList.add(EventsModel(2,"RESET","Reception","User", LocalDateTime.now()))
        eventsList.add(EventsModel(3,"ATTACK","HALL","User", LocalDateTime.now()))
        eventsList.add(EventsModel(4,"DECLINE","BACKYARD","User", LocalDateTime.now()))
        for (item in eventsList){
            item.save()
        }

        val listAll = EventsModel.listAll(EventsModel::class.java)
        val user = getUser();
        println("Total save events Model ----------------- "+ listAll.toString());
        eventsListView.adapter = EventsAdapter(listAll)
        eventsListView.layoutManager = LinearLayoutManager(this)
        val retrofit = RetrofitInstance.getInstance();
        var service = retrofit.create(ApiService::class.java);
        val context= this;
        val handler = Handler();
        val delay = 3000L
        val runnable = object : Runnable  {
            override fun run() {
                GlobalScope.launch {
                    try {
                        println("starting the call *****************")
                        val call: Call<String> = service.getEvents(user.channelId,user.Password)
                        println("Request url -> "+ call.request().url);
                        call.enqueue(object : Callback<String?> {
                            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                                val plain_text_response = response.body()
                                var result: List<String> = plain_text_response.toString().split(",").map { it.trim() }
                                if(result.size>1 && !result.contains("LAN Fault")){
                                    setDataToDB(result)
                                }
                                println("response*********************************************"+plain_text_response)
                                val listAll = EventsModel.listAll(EventsModel::class.java)
                                eventsListView.adapter = EventsAdapter(listAll)
                                eventsListView.layoutManager = LinearLayoutManager(context)
                                println(listAll)
                            }

                            override fun onFailure(call: Call<String?>, t: Throwable) {
                                println(t.localizedMessage)
                                println(t.stackTrace)
                                println("error*********************************************");
                            }
                        })
                    }catch (e: Exception){
                        e.printStackTrace();
                    }
                }
                handler.postDelayed(this,delay)


            }
        } //runnable ends here

        // Get the event time from a saved instance state or use the current time
        eventTime = savedInstanceState?.getSerializable("eventTime") as? Instant ?: Instant.now()

        // Start the coroutine to update the elapsed time
//        startElapsedTimeCoroutine()


    }

//    private fun handleAckButton(){
//        btn_ack.setOnClickListener {
//            var usernameTextView = findViewById<>()
//        }
//    }
    private fun setUser(){
        val userName = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")
        val listAll = UserModel.listAll(UserModel::class.java)

        if(listAll.isNotEmpty() && (userName.toString().isNotEmpty() && password.toString().isNotEmpty())) {
            for (item in listAll){
                item.delete()
            }
            val currentUser = UserModel();
            currentUser.channelId = userName;
            currentUser.Password = password;
            currentUser.save()

        }
    }
    private fun setDataToDB(data : List<String>){
        if(data.isNotEmpty()) {
            val event = EventsModel()
            for (item in data.indices) {
                if (data[item] != null && item == 4) {
                    event.systemName = data[item]
                } else if (data[item] != null && item == 5) {
                    event.address = data[item]
                } else if (data[item] != null && item == 6) {
                    event.eventName = data[item]
                } else if (data[item] != null && item == 7) {
                    event.username = data[item]
                }
            }
            event.save()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        AppUtils.animateEnterLeft(this)
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

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//        // Save the event time to a saved instance state
//        outState.putSerializable("eventTime", eventTime)
//    }
//
//    private fun startElapsedTimeCoroutine() {
//        GlobalScope.launch(Dispatchers.Main) {
//            while (true) {
//                // Get the current time
//                val currentTime = Instant.now()
//
//                // Calculate the elapsed time in seconds
//                val elapsedTimeSeconds = ChronoUnit.SECONDS.between(eventTime, currentTime)
//
//                // Convert elapsed time to minutes and seconds
//                val minutes = elapsedTimeSeconds / 60
//                val secondsLeft = elapsedTimeSeconds % 60
//
//                // Update the elapsed time UI element
//                elapsed_time_textview = findViewById(R.id.elapsed_time_textview)
//                elapsed_time_textview.text = "Elapsed time: $minutes min $secondsLeft sec"
//
//                // Delay for 1 second before the next update
//                delay(1000)
//            }
//        }
//    }
}