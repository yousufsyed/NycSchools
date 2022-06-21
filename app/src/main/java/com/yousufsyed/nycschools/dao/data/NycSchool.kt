package com.yousufsyed.nycschools.dao.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NYC_SCHOOL_TABLE_NAME = "nyc_school_table"

@Entity(tableName = NYC_SCHOOL_TABLE_NAME)
class NycSchool(

    @PrimaryKey
    @ColumnInfo(name = "dbn")
    val schoolId: String,

    @ColumnInfo(name = "school_name")
    val name: String,

    @ColumnInfo(name = "overview_paragraph")
    val overview: String?,

    @ColumnInfo(name = "neighborhood")
    val neighborhood: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "phone_number")
    val phone: String?,

    @ColumnInfo(name = "fax_number")
    val fax: String?,

    @ColumnInfo(name = "school_email")
    val email: String?,

    @ColumnInfo(name = "website")
    val website: String?,

    @ColumnInfo(name = "finalgrades")
    val grades: String?,

    @ColumnInfo(name = "total_students")
    val totalStudents: Long?,

    @ColumnInfo(name = "total_sat_takers")
    val totalTestTakers: String?,

    @ColumnInfo(name = "sat_reading_score")
    val criticalReadingScore: String?,

    @ColumnInfo(name = "sat_math_score")
    val satMathScore: String?,

    @ColumnInfo(name = "sat_writing_score")
    val satWritingScore: String?
)