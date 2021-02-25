package com.peopleinteractive.shaadi.data.model

import com.peopleinteractive.shaadi.data.db.entity.People
import com.squareup.moshi.Json

/**
 * PeopleResponse is simple pojo class for the network response.
 */
data class PeopleResponse(
    @Json(name = "results")
    val peoplesList: List<People>
)
