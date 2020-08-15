package com.example.boughtlist

import android.app.Application
import io.realm.Realm

class boughtListApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}