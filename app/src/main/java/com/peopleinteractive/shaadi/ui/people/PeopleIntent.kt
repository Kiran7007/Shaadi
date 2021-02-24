package com.peopleinteractive.shaadi.ui.people

sealed class PeopleIntent {

    object FetchRemotePeople : PeopleIntent()
    object FetchLocalPeople : PeopleIntent()
}