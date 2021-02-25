package com.peopleinteractive.shaadi.data.db.dao

import androidx.room.*
import com.peopleinteractive.shaadi.data.db.entity.People
import kotlinx.coroutines.flow.Flow

/**
 * PeopleDao manages all the database queries.
 */
@Dao
interface PeopleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(peoples: List<People>): LongArray

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(people: People): Long

    @Query("SELECT * FROM people ORDER BY first ASC, last ASC")
    fun fetchPeoples(): Flow<List<People>>

    @Update
    suspend fun update(people: People)
}