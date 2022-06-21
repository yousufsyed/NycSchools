package com.yousufsyed.nycschools.viewmodel

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.yousufsyed.nycschools.network.data.NycSchoolsResults
import com.yousufsyed.nycschools.nycSchool
import com.yousufsyed.nycschools.provider.DispatcherProvider
import com.yousufsyed.nycschools.provider.EventLogger
import com.yousufsyed.nycschools.provider.NycSchoolsProvider
import io.kotest.core.spec.style.DescribeSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class NycSchoolsViewModelTest : DescribeSpec({

    //Dispatchers.setMain(UnconfinedTestDispatcher())
    ArchTaskExecutor.getInstance().setDelegate(InstantTaskExecutorForSpec)

    val result = NycSchoolsResults.Success(listOf(nycSchool))
    val eventLogger: EventLogger = mock()
    val observer: Observer<in NycSchoolsResults> = mock()

    val dispatcherProvider = mock<DispatcherProvider> {
        on { io } doReturn UnconfinedTestDispatcher()
    }

    val nycSchoolsProvider: NycSchoolsProvider = mock()
    nycSchoolsProvider.stub {
        onBlocking { getSchools() }.doReturn(result)
    }

    // TODO Fixme: Runs in isolation but fails when running testDebugUnitTest
    //  https://github.com/mockito/mockito-kotlin/issues/379
    xdescribe("When livedata is observed") {

        val nycViewModel = NycSchoolsViewModel(nycSchoolsProvider, dispatcherProvider, eventLogger)

        nycViewModel.nycSchoolsStateLiveData.observeForever(observer)

        val value = nycViewModel.nycSchoolsStateLiveData.value

        it("should emit nycSchools") {
            assertEquals(value, result)
        }
    }

})

// this is just the same code as the InstantTaskExecutorRule
val InstantTaskExecutorForSpec = object : TaskExecutor() {
    override fun executeOnDiskIO(runnable: Runnable) { runnable.run() }
    override fun postToMainThread(runnable: Runnable) { runnable.run() }
    override fun isMainThread(): Boolean = true
}