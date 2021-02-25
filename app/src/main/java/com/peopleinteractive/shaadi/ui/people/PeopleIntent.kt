package com.peopleinteractive.shaadi.ui.people

/**
 * PeopleIntent triggers the event to the viewModel.
 */
sealed class PeopleIntent {

    /**
     * Trigger the intent to fetch the data from remote server.
     */
    object FetchRemotePeople : PeopleIntent()

    /**
     * Trigger the intent to fetch the data from local database.
     */
    object FetchLocalPeople : PeopleIntent()
}