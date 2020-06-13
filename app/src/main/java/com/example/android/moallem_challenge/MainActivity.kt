package com.example.android.moallem_challenge

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(Color.WHITE);
      //  val root = LayoutInflater.from(applicationContext).inflate(R.layout.activity_main, null)
        supportActionBar?.elevation = 0F

    }
}
