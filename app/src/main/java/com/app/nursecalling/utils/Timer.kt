package com.app.nursecalling.utils
import java.util.*
class TimerApi {


    fun main() {
        val startTime = System.currentTimeMillis()
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val elapsedTime = (currentTime - startTime) / 1000
                println("Elapsed time is: $elapsedTime seconds")
            }
        }
        timer.schedule(task, 0L, 1000L)
    }
}