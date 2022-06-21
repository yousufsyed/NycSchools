package com.yousufsyed.nycschools.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yousufsyed.nycschools.dao.data.NycSchool

const val NYC_SCHOOLS_DB = "nyc_school_database"

@Database(entities = [NycSchool::class], version = 2, exportSchema = false)
abstract class NycSchoolDatabase : RoomDatabase() {
    abstract fun nycSchoolsDao(): NycSchoolsDao
}