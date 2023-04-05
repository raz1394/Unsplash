package com.example.unsplash.splash


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unsplash.dashboard.DashboardActivity


class LaunchScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this@LaunchScreenActivity, DashboardActivity::class.java))
        finish()
    }
}