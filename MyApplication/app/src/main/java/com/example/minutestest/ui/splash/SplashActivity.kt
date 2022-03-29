package com.example.minutestest.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.minutestest.ui.main.MainActivity

class SplashActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}