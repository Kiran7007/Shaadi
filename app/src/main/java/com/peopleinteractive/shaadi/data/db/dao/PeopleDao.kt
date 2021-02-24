package com.peopleinteractive.shaadi.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg peoples: People): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: People): Long

    @Query("SELECT * FROM people")
    fun fetchPeoples(): Flow<List<People>>
}