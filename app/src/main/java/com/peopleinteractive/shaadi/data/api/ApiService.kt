package com.peopleinteractive.shaadi.data.api

import com.peopleinteractive.shaadi.data.model.PeopleResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * ApiService is responsible to define end points and its responses.
 */
interface ApiService {

    @GET("api/?results=10")
    suspend fun fetchPeoples(): Response<PeopleResponse>
}