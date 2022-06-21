package com.yousufsyed.nycschools.network

import com.yousufsyed.nycschools.network.data.NycSchoolInfo
import com.yousufsyed.nycschools.network.data.SatScores
import javax.inject.Inject

interface NycSchoolRestClient {

    /**
     * Fetch Schools
     */
    suspend fun getSchools(): List<NycSchoolInfo>

    /**
     * Fetch school details
     */
    suspend fun getSatScores(): List<SatScores>
}

class DefaultNycSchoolRestClient @Inject constructor(
    private val nycSchoolApi: NycSchoolsApi
): NycSchoolRestClient {

    override suspend fun getSchools(): List<NycSchoolInfo> =
        nycSchoolApi.getSchools()

    override suspend fun getSatScores() : List<SatScores> =
        nycSchoolApi.getSatScores()
}