package com.peopleinteractive.shaadi.ui.people

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import com.peopleinteractive.shaadi.util.Mapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PeopleViewModel(private val repository: PeopleRepository) : ViewModel() {

    companion object {
        private val TAG = PeopleViewModel::class.java.simpleName
    }

    val peopleIntent = Channel<PeopleIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<PeopleState>(PeopleState.Idle)
    val state: StateFlow<PeopleState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            peopleIntent.consumeAsFlow().collect {
                when (it) {
                    is PeopleIntent.FetchRemotePeople -> requestPeoples()
                    is PeopleIntent.FetchLocalPeople -> fetchDataFromLocal()
                }
            }
        }
    }

    private fun fetchDataFromLocal() {
        viewModelScope.launch {
            repository.fetchDataFromDB().collect {
                _state.value = PeopleState.PeopleData(it)
            }
        }
    }

    private fun requestPeoples() {
        viewModelScope.launch {
            try {
                _state.value = PeopleState.Loading(true)
                when (val fetchPeoples = repository.fetchPeoples()) {
                    is Result.Success -> {
                        _state.value = PeopleState.PeopleData(fetchPeoples.data)
                    }
                    is Result.Error -> {
                        _state.value = PeopleState.Error(fetchPeoples.exception.message)
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
        _state.value = PeopleState.Error("Accepted : ${Mapper.getPersonName(people)}")
    }

    fun decline(people: People) {
        _state.value = PeopleState.Error("Declined : ${Mapper.getPersonName(people)}")
    }
}