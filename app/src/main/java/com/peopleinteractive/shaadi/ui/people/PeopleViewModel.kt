package com.peopleinteractive.shaadi.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.repository.PeopleRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class PeopleViewModel(private val repository: PeopleRepository) : ViewModel() {

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

    fun fetchDataFromLocal() {
        viewModelScope.launch {
            repository.fetchDataFromDB().collect {
                _state.value = PeopleState.PeopleData(it)
            }
        }
    }


    private fun requestPeoples() {
        viewModelScope.launch {
            _state.value = PeopleState.Loading
            when (repository.fetchPeoples()) {
                is Result.Success -> {
                    _state.value = PeopleState.PeopleData(emptyList())
                }
                is Result.Error -> {
                    _state.value = PeopleState.Error("")
                }
            }
        }
    }
}