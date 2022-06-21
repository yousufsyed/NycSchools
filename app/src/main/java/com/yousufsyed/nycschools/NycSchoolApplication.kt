package com.yousufsyed.nycschools

import android.app.Application
import com.yousufsyed.nycschools.di.AppComponent
import com.yousufsyed.nycschools.di.DaggerAppComponent

class NycSchoolApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

}