package com.swivelgroup.newsticker.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.view.home.MainActivity
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
