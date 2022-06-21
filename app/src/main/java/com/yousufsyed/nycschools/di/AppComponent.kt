package com.yousufsyed.nycschools.di

import android.app.Application
import com.yousufsyed.nycschools.view.SchoolsListFragment
import com.yousufsyed.nycschools.view.NycSchoolsActivity
import com.yousufsyed.nycschools.view.SchoolDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    fun inject(mainActivity: NycSchoolsActivity)
    fun inject(schoolsListFragment: SchoolsListFragment)
    fun inject(schoolsDetailsFragment: SchoolDetailsFragment)
}