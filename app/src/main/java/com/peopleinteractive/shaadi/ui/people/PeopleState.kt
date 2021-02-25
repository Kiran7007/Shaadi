package com.peopleinteractive.shaadi.ui.people

sealed class PeopleState {
    object Idle : PeopleState()
    data class Loading(val isLoading: Boolean) : PeopleState()
    data class Error(val message: String?) : PeopleState()
}