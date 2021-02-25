package com.peopleinteractive.shaadi.ui.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import com.peopleinteractive.shaadi.util.ACCEPTED
import com.peopleinteractive.shaadi.util.DECLINED
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * PeopleViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class PeopleViewModel(private val repository: PeopleRepository) : ViewModel() {

    companion object {
        private val TAG = PeopleViewModel::class.java.simpleName
    }

    /**
     * Observes the intent.
     */
    val peopleIntent = Channel<PeopleIntent>(Channel.UNLIMITED)

    /**
     * Manage the states.
     */
    private val _state = MutableStateFlow<PeopleState>(PeopleState.Idle)
    val state: StateFlow<PeopleState> get() = _state

    /**
     * Provides the data to the view in the form of live data.
     */
    val peoples: LiveData<List<People>> get() = repository.fetchDataFromDB().asLiveData()

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            peopleIntent.consumeAsFlow().collect {
                when (it) {
                    is PeopleIntent.FetchRemotePeople -> fetchDataFromRemote()
                    is PeopleIntent.FetchLocalPeople -> fetchDataFromLocal()
                }
            }
        }
    }

    /**
     * Gets the data from the local database.
     */
    private fun fetchDataFromLocal() {
        if (peoples.value.isNullOrEmpty()) {
            fetchDataFromRemote()
        }
    }

    /**
     * Gets the data from the remote server.
     */
    private fun fetchDataFromRemote() {
        viewModelScope.launch {
            try {
                _state.value = PeopleState.Loading(true)
                when (val fetchPeoples = repository.fetchRemotePeoples()) {
                    is Result.Success -> {
                        withContext(Dispatchers.IO) { repository.insert(fetchPeoples.data) }
                    }
                    is Result.Error -> {
                        _state.value =
                            PeopleState.Error("Please check the network connection and try again")
                    }
                }
                _state.value = PeopleState.Loading(false)
            } catch (e: Exception) {
                Log.d(TAG, "Error while fetching the data")
                _state.value = PeopleState.Loading(false)
            }
        }
    }

    /**
     * Gets the accept event and updates in the database.
     */
    fun accept(people: People) {
        viewModelScope.launch {
            people.connection.isUpdated = true
            people.connection.connectionStatus = ACCEPTED
            people.connection.updatedAt = System.currentTimeMillis()
            repository.update(people)
        }
    }

    /**
     * Gets the decline event and updates in the database.
     */
    fun decline(people: People) {
        viewModelScope.launch {
            people.connection.isUpdated = true
            people.connection.connectionStatus = DECLINED
            people.connection.updatedAt = System.currentTimeMillis()
            repository.update(people)
        }
    }
}