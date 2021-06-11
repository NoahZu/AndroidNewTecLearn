package io.github.noahzu.androidnewteclearn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.noahzu.annotation.EventReceive

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @EventReceive(eventId = 1)
    fun onReceiveGift(string : String) {

    }
}