package com.peopleinteractive.shaadi.data.repository

import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.flow.Flow

/**
 * PeopleRepository manages the transactions of data layer and network layer.
 */
interface PeopleRepository {

    /**
     * Fetch the data from the remote server.
     */
    suspend fun fetchRemotePeoples(): Result<List<People>>

    /**
     * Fetch the data from location database
     */
    fun fetchDataFromDB(): Flow<List<People>>

    /**
     * Update the people entity in the database.
     */
    suspend fun update(people: People)

    /**
     * Insert the list of peoples in the database.
     */
    suspend fun insert(data: List<People>): LongArray
}