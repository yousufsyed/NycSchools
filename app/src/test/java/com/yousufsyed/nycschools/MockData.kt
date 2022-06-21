package com.yousufsyed.nycschools

import com.yousufsyed.nycschools.dao.data.NycSchool
import com.yousufsyed.nycschools.network.data.NycSchoolInfo
import com.yousufsyed.nycschools.network.data.SatScores

val mockResponse = "[{\"dbn\": \"01M292\",\"school_name\":\"Clinton School Writers & Artists\",\"num_of_sat_test_takers\": \"29\",\"sat_critical_reading_avg_score\": \"355\", \"sat_math_avg_score\": \"404\",\"sat_writing_avg_score\": \"363\" }]"

val nycSchool = NycSchool(
    schoolId = "01M292",
    name = "Clinton School Writers & Artists",
    neighborhood = "Chelsea-Union Sq",
    location = "10 East 15th Street, Manhattan NY 10003",
    phone = "212-524-4360",
    fax = "212-524-4365",
    email = "admissions@theclintonschool.net",
    website = "www.theclintonschool.net",
    totalTestTakers = "29",
    criticalReadingScore = "355",
    satMathScore = "404",
    satWritingScore = "363",
    grades = "6-12",
    overview = "Students who are prepared for college",
    totalStudents = 376
)

val nycSchoolInfo = NycSchoolInfo(
    dbn = "01M292",
    school_name = "Clinton School Writers & Artists",
    neighborhood = "Chelsea-Union Sq",
    location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)",
    phone_number = "212-524-4360",
    fax_number = "212-524-4365",
    school_email = "admissions@theclintonschool.net",
    website = "www.theclintonschool.net",
    finalgrades = "6-12",
    overview_paragraph = "Students who are prepared for college",
    totalStudents = 376
)

val satScores = SatScores(
    dbn = "01M292",
    num_of_sat_test_takers = "29",
    sat_critical_reading_avg_score = "355",
    sat_math_avg_score = "404",
    sat_writing_avg_score = "363"
)