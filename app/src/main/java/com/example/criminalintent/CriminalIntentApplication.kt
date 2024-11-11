package com.example.criminalintent

import android.app.Application

class CriminalIntentApplication: Application() {  // creates CriminalIntent Application subclass that overrides default Application class onCreate function at boot.  Registered in AndroidManifest.xml

    override fun onCreate() {  // overrides
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}