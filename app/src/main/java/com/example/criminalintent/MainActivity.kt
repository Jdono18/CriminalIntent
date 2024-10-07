package com.example.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)  // retrieves CrimeFragment from FragmentManager by it's container view id

        if (currentFragment == null) {
            val fragment = CrimeFragment()
            supportFragmentManager  // calls the activities FragmentManager
                .beginTransaction()  // creates and returns an instance of FragmentTransaction
                .add(R.id.fragment_container, fragment)  // creates and commits a fragment transaction.  Container View ID = fragment_container which is the resourceID of FrameLayout from activity_main.xml
                .commit()  // commits fragment transaction
        }
    }
}