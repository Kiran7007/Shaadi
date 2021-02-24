package com.peopleinteractive.shaadi.data.repository

import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    suspend fun fetchRemotePeoples(): Result<List<People>>

    fun fetchDataFromDB(): Flow<List<People>>

    suspend fun update(people: People)

    suspend fun insert(data: List<People>): LongArray
}