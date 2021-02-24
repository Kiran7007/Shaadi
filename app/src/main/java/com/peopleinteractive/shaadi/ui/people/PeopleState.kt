package com.peopleinteractive.shaadi.ui.people

import com.peopleinteractive.shaadi.data.db.entity.People

sealed class PeopleState {
    object Idle : PeopleState()
    data class Loading(val isLoading: Boolean) : PeopleState()
    data class Error(val message: String?) : PeopleState()
    data class PeopleData(val peoples: List<People>) : PeopleState()
}