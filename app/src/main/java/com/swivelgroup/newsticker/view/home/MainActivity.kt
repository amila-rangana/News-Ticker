package com.swivelgroup.newsticker.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.swivelgroup.newsticker.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadNewsFragment()
    }

    private fun loadNewsFragment(){
        val fragment = NewsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment)
            .disallowAddToBackStack().commit()
    }
}
