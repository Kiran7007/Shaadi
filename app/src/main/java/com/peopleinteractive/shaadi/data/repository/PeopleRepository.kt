package com.peopleinteractive.shaadi.data.repository

import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    suspend fun fetchPeoples(): Result<List<People>>

    suspend fun fetchDataFromDB(): Flow<List<People>>
}