package com.peopleinteractive.shaadi.data.repository

import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.api.ApiService
import com.peopleinteractive.shaadi.data.db.dao.PeopleDao
import com.peopleinteractive.shaadi.data.db.entity.People

/**
 * PeopleRepositoryImpl responsible for doing database and network transactions.
 */
class PeopleRepositoryImpl(
    private val apiService: ApiService,
    private val peopleDao: PeopleDao
) : PeopleRepository {

    companion object {
        private val TAG = PeopleRepository::class.java.simpleName
    }

    override suspend fun fetchRemotePeoples(): Result<List<People>> {
        return try {
            val response = apiService.fetchPeoples()
            if (response.isSuccessful) {
                Result.Success(response.body()?.peoplesList ?: emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun fetchDataFromDB() = peopleDao.fetchPeoples()

    override suspend fun update(people: People) = peopleDao.update(people)

    override suspend fun insert(data: List<People>) = peopleDao.insert(data)
}