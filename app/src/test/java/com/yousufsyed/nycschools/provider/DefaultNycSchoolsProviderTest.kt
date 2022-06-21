package com.yousufsyed.nycschools.provider

import com.yousufsyed.nycschools.dao.NycSchoolsDao
import com.yousufsyed.nycschools.network.NycSchoolRestClient
import com.yousufsyed.nycschools.network.data.NycSchoolsResults
import com.yousufsyed.nycschools.nycSchool
import com.yousufsyed.nycschools.nycSchoolInfo
import com.yousufsyed.nycschools.satScores
import io.kotest.core.spec.style.DescribeSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.*

@ExperimentalCoroutinesApi
class DefaultNycSchoolsProviderTest : DescribeSpec({

    val mockList = listOf(nycSchool)
    val mockSatScoresList = listOf(satScores)
    val mockNycSchoolInfoList = listOf(nycSchoolInfo)
    val eventLogger = mock<EventLogger>()

    val testDispatcher = UnconfinedTestDispatcher()

    val nycSchoolRestClient = mock<NycSchoolRestClient>()
    val networkAvailabilityProvider = mock<NetworkAvailabilityProvider>()
    val nycSchoolsDao = mock<NycSchoolsDao>()
    val dispatcherProvider = mock<DispatcherProvider>()

    val nycSchoolsProvider = DefaultNycSchoolsProvider(
        nycSchoolRestClient = nycSchoolRestClient,
        nycSchoolsDao = nycSchoolsDao,
        networkAvailabilityProvider = networkAvailabilityProvider,
        dispatcherProvider = dispatcherProvider,
        eventLogger = eventLogger
    )

    // TODO Fixme: Runs in isolation but fails when running testDebugUnitTest
    //  https://github.com/mockito/mockito-kotlin/issues/379
    xdescribe("When FetchSchools is called") {

        whenever(dispatcherProvider.io).thenReturn(testDispatcher)
        whenever(networkAvailabilityProvider.isConnectedToNetwork()).thenReturn(true)
        whenever(nycSchoolRestClient.getSchools()).thenReturn(mockNycSchoolInfoList)
        whenever(nycSchoolRestClient.getSatScores()).thenReturn(mockSatScoresList)
        whenever(nycSchoolsDao.getNycSchools()).thenReturn(mockList)

        val result = nycSchoolsProvider.getSchools()

        it("returns NycSchoolsResult") {
            assertThat(result)
                .isEqualToComparingFieldByField(
                    NycSchoolsResults.Success(mockList)
                )
        }

        it("gets data from db") {
            verify(nycSchoolsDao, times(1)).getNycSchools()
        }

        it("never fetches data from network") {
            verify(nycSchoolRestClient, never()).getSchools()
        }
    }
})