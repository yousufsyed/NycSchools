package com.yousufsyed.nycschools

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yousufsyed.nycschools.di.AppComponent
import java.lang.RuntimeException

// Inflater Extension
val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

// App component Extension
val Fragment.component : AppComponent
    get() = run {
        (requireActivity().application as? NycSchoolApplication)?.appComponent
            ?: throw AppComponentNotFoundException()
    }

class AppComponentNotFoundException : RuntimeException()