package com.demarkelabs.android_parsesdk_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseQuery
import fragment.ComposeFragment
import fragment.FeedFragment
import fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener {
                item ->

            var fragmentToShow: Fragment? = null
            when (item.itemId) {
                R.id.action_home -> {
                    fragmentToShow = FeedFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }
            }

            if (fragmentToShow != null) {
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }

            true
        }

        // set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home
    }

    companion object {
        const val TAG = "MainActivity"
    }
}