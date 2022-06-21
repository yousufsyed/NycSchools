package com.yousufsyed.nycschools.provider

import com.yousufsyed.nycschools.dao.data.NycSchool
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface SchoolProvider {
    fun get() : StateFlow<NycSchool?>
    fun set(nycSchool: NycSchool)
}

class DefaultSchoolProvider @Inject constructor() : SchoolProvider {

    private val nycSchoolFlow = MutableStateFlow<NycSchool?>(null)

    override fun get(): StateFlow<NycSchool?> = nycSchoolFlow

    override fun set(nycSchool: NycSchool) {
        nycSchoolFlow.value = nycSchool
    }
}