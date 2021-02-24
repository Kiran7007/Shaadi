package com.peopleinteractive.shaadi.util

import com.peopleinteractive.shaadi.data.db.entity.People

object Mapper {

    @JvmStatic
    fun getPersonName(people: People) =
        "${people.name.title}. ${people.name.first} ${people.name.last}"

    @JvmStatic
    fun getPersonDetails(people: People) =
        "${people.dob.age}, ${people.phone}, ${people.gender}"

    @JvmStatic
    fun getPersonLocation(people: People) =
        "${people.location.city}, ${people.location.country}, ${people.location.state}-${people.location.postcode}"
}