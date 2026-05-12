package com.example.mamabear

import android.app.Application
import com.google.firebase.FirebaseApp

class MamaBearApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}
