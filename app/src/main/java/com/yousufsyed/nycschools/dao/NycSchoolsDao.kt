package com.yousufsyed.nycschools.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yousufsyed.nycschools.dao.data.NYC_SCHOOL_TABLE_NAME
import com.yousufsyed.nycschools.dao.data.NycSchool
import kotlinx.coroutines.flow.Flow

@Dao
interface NycSchoolsDao {

    // Schools list
    @Query("SELECT * FROM $NYC_SCHOOL_TABLE_NAME")
    fun getNycSchoolsAsFlow(): Flow<List<NycSchool>>

    // Schools list
    @Query("SELECT * FROM $NYC_SCHOOL_TABLE_NAME")
    suspend fun getNycSchools(): List<NycSchool>

    @Query("SELECT COUNT(*) FROM $NYC_SCHOOL_TABLE_NAME")
    suspend fun getNycSchoolsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSchools(nycSchools: List<NycSchool>)

    @Query("DELETE FROM $NYC_SCHOOL_TABLE_NAME")
    suspend fun deleteNycSchools()
}