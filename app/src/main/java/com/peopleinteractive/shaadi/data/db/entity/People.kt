package com.peopleinteractive.shaadi.data.db.entity

import androidx.room.*
import com.squareup.moshi.Json

@Entity(tableName = "people")
data class People(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email")
    @Json(name = "email")
    val email: String,

    @Embedded
    @Json(name = "name")
    val name: Name,

    @Embedded
    @Json(name = "dob")
    val dob: Dob,

    @Embedded
    @Json(name = "picture")
    val picture: Picture,

    @Embedded
    @Json(name = "location")
    val location: Location,

    @Embedded
    @Transient
    var connection: Connection = Connection(false, "", 0),

    @ColumnInfo(name = "gender")
    @Json(name = "gender")
    val gender: String,

    @ColumnInfo(name = "phone")
    @Json(name = "phone")
    val phone: String,

    @ColumnInfo(name = "cell")
    @Json(name = "cell")
    val cell: String,
)

data class Name(
    @ColumnInfo(name = "first")
    @Json(name = "first")
    val first: String,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    val title: String,

    @ColumnInfo(name = "last")
    @Json(name = "last")
    val last: String
)

data class Dob(
    @ColumnInfo(name = "date")
    @Json(name = "date")
    val date: String,

    @ColumnInfo(name = "age")
    @Json(name = "age")
    val age: Int
)

data class Picture(
    @ColumnInfo(name = "large")
    @Json(name = "large")
    val large: String,

    @ColumnInfo(name = "medium")
    @Json(name = "medium")
    val medium: String,

    @ColumnInfo(name = "thumbnail")
    @Json(name = "thumbnail")
    val thumbnail: String
)

data class Location(
    @ColumnInfo(name = "city")
    @Json(name = "city")
    val city: String,

    @ColumnInfo(name = "state")
    @Json(name = "state")
    val state: String,

    @ColumnInfo(name = "country")
    @Json(name = "country")
    val country: String,

    @ColumnInfo(name = "postcode")
    @Json(name = "postcode")
    val postcode: String
)

data class Connection(
    @ColumnInfo(name = "isUpdated")
    var isUpdated: Boolean,

    @ColumnInfo(name = "connectionStatus")
    var connectionStatus: String,

    @ColumnInfo(name = "updatedAt")
    var updatedAt: Long
)
