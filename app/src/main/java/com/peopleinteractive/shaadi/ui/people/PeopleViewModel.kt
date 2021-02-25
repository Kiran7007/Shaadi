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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeopleViewModel(private val repository: PeopleRepository) : ViewModel() {

    companion object {
        private val TAG = PeopleViewModel::class.java.simpleName
    }

    val peopleIntent = Channel<PeopleIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PeopleState>(PeopleState.Idle)
    val state: StateFlow<PeopleState> get() = _state

    val peoples: LiveData<List<People>> get() = repository.fetchDataFromDB().asLiveData()

    init {
        handleIntent()
    }

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

    private fun fetchDataFromLocal() {
        if (peoples.value.isNullOrEmpty()) {
            fetchDataFromRemote()
        }
    }

    private fun fetchDataFromRemote() {
        viewModelScope.launch {
            try {
                _state.value = PeopleState.Loading(true)
                when (val fetchPeoples = repository.fetchRemotePeoples()) {
                    is Result.Success -> {
                        withContext(Dispatchers.IO) { repository.insert(fetchPeoples.data) }
                    }
                    is Result.Error -> {
                        _state.value = PeopleState.Error("Please check the network connection and try again")
                    }
                }
                _state.value = PeopleState.Loading(false)
            } catch (e: Exception) {
                Log.d(TAG, "Error while fetching the data")
                _state.value = PeopleState.Loading(false)
            }
        }
    }

    fun accept(people: People) {
        viewModelScope.launch {
            people.connection.isUpdated = true
            people.connection.connectionStatus = ACCEPTED
            people.connection.updatedAt = System.currentTimeMillis()
            repository.update(people)
        }
    }

    fun decline(people: People) {
        viewModelScope.launch {
            people.connection.isUpdated = true
            people.connection.connectionStatus = DECLINED
            people.connection.updatedAt = System.currentTimeMillis()
            repository.update(people)
        }
    }
}