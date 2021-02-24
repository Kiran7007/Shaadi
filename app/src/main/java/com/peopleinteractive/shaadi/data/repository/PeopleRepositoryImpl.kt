package com.peopleinteractive.shaadi.data.repository

import android.util.Log
import com.peopleinteractive.shaadi.data.Result
import com.peopleinteractive.shaadi.data.api.ApiService
import com.peopleinteractive.shaadi.data.db.dao.PeopleDao
import com.peopleinteractive.shaadi.data.db.entity.People

class PeopleRepositoryImpl(
    private val apiService: ApiService,
    private val peopleDao: PeopleDao
) : PeopleRepository {

    companion object {
        private val TAG = PeopleRepository::class.java.simpleName
    }

    override suspend fun fetchPeoples(): Result<List<People>> {
        return try {
            val response = apiService.fetchPeoples()
            Log.d(TAG, "Response : ${response.body()}")
            if (response.isSuccessful) {
                Result.Success(response.body()?.peoplesList ?: emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun fetchDataFromDB() = peopleDao.fetchPeoples()
}