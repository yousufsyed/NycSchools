package com.yousufsyed.nycschools.provider

import com.yousufsyed.nycschools.dao.NycSchoolsDao
import com.yousufsyed.nycschools.dao.data.NycSchool
import com.yousufsyed.nycschools.network.NycSchoolRestClient
import com.yousufsyed.nycschools.network.data.NycSchoolsResults
import com.yousufsyed.nycschools.network.data.mergeResults
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NycSchoolsProvider {

    /**
     * Fetch School List from Network
     */
    suspend fun getSchools(): NycSchoolsResults

    fun getSchoolsAsFlow(): Flow<List<NycSchool>>
}

/**
 * Class to provide schools related data
 */
class DefaultNycSchoolsProvider @Inject constructor(
    private val nycSchoolRestClient: NycSchoolRestClient,
    private val nycSchoolsDao: NycSchoolsDao,
    private val networkAvailabilityProvider: NetworkAvailabilityProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val eventLogger: EventLogger
) : NycSchoolsProvider, EventLogger by eventLogger {

    override fun getSchoolsAsFlow(): Flow<List<NycSchool>> {
        return nycSchoolsDao.getNycSchoolsAsFlow()
    }

    override suspend fun getSchools(): NycSchoolsResults {
        return withContext(dispatcherProvider.io) {
            try {
                if(nycSchoolsDao.getNycSchoolsCount() == 0) {
                // if results are not in DB then fetch from network
                    fetchSchools()
                    logSuccess(SCHOOLS_FETCH_SUCCESS_MESSAGE)
                }
                NycSchoolsResults.Success
            } catch (exception: Exception) {
                    logError(exception)
                    NycSchoolsResults.Error(exception)
            }

        }
    }

    private suspend fun fetchSchools() {
        if (!networkAvailabilityProvider.isConnectedToNetwork()) {
            throw NetworkConnectivityError()
        }
        return withContext(dispatcherProvider.io) {
            val schoolsList = async { nycSchoolRestClient.getSchools() }
            val satScores = async { nycSchoolRestClient.getSatScores() }
            val results = mergeResults(schoolsList.await(), satScores.await())
            saveSchools(results)
        }
    }

    private suspend fun saveSchools(nycSchools: List<NycSchool>) {
        return withContext(dispatcherProvider.io) {
            runCatching {
                nycSchoolsDao.insertAllSchools(nycSchools)
            }
                .onFailure { logError(it) }
                .onSuccess { logSuccess("Schools list saved successfully") }
        }
    }
}

class NetworkConnectivityError : RuntimeException()