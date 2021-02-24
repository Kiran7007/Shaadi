package com.peopleinteractive.shaadi.data.repository

import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.api.ApiService
import com.peopleinteractive.shaadi.data.db.dao.PeopleDao
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PeopleRepositoryImpl(
    private val apiservice: ApiService
    , private val peopleDao: PeopleDao
) : PeopleRepository {

    override suspend fun fetchPeoples(): Result<List<People>> {
        return try {
            val response = withContext(Dispatchers.IO) { apiservice.fetchPeoples() }
            if (response.isSuccessful) {
                Result.Success(emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchDataFromDB()= peopleDao.fetchPeoples()
}