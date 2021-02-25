package com.peopleinteractive.shaadi.ui.people

/**
 * PeopleState holds the last state of the variable.
 */
sealed class PeopleState {

    /**
     * Initial state of the view.
     */
    object Idle : PeopleState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : PeopleState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : PeopleState()
}