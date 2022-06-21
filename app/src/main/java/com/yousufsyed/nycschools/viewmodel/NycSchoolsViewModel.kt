package com.yousufsyed.nycschools.viewmodel

import androidx.lifecycle.*
import com.yousufsyed.nycschools.network.data.NycSchoolsResults
import com.yousufsyed.nycschools.provider.DefaultDispatcherProvider
import com.yousufsyed.nycschools.provider.DispatcherProvider
import com.yousufsyed.nycschools.provider.EventLogger
import com.yousufsyed.nycschools.provider.NycSchoolsProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class NycSchoolsViewModel @Inject constructor(
    private val nycSchoolsProvider: NycSchoolsProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val eventLogger: EventLogger
) : ViewModel(), EventLogger by eventLogger {

    private val _nycSchoolsStateLiveData: MutableLiveData<NycSchoolsResults> = MutableLiveData<NycSchoolsResults>()

    init {
        fetchSchools()
    }

    private fun fetchSchools() {
        _nycSchoolsStateLiveData.postValue(NycSchoolsResults.Loading)

        viewModelScope.launch(dispatcherProvider.io){
            logMessage("Fetching schools from init")
            val results = nycSchoolsProvider.getSchools()
            _nycSchoolsStateLiveData.postValue(results)
            logMessage("sent Fetched schools to livedata")
        }
    }

    fun retryFetch() {
        fetchSchools()
    }

    val nycSchoolsStateLiveData: LiveData<NycSchoolsResults> = _nycSchoolsStateLiveData
}

class NycSchoolsViewModelFactory @Inject constructor(
    private val nycSchoolsViewModel: NycSchoolsViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NycSchoolsViewModel::class.java)) {
            return nycSchoolsViewModel as T
        }
        throw IllegalArgumentException("Unknown View-model")
    }
}