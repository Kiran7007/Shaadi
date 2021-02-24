package com.peopleinteractive.shaadi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peopleinteractive.shaadi.data.db.dao.PeopleDao
import com.peopleinteractive.shaadi.data.db.entity.People
import com.peopleinteractive.shaadi.util.DATABASE_VERSION

@Database(
    entities = [People::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}