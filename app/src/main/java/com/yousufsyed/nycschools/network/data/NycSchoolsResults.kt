package com.yousufsyed.nycschools.network.data

import com.yousufsyed.nycschools.dao.data.NycSchool

sealed class NycSchoolsResults {
    object Loading : NycSchoolsResults()
    object Success: NycSchoolsResults()
    data class Error(val throwable: Throwable) : NycSchoolsResults()
}

data class NycSchoolInfo(

    val dbn: String,

    val school_name: String?,

    val overview_paragraph: String?,

    val neighborhood: String?,

    val location: String?,

    val phone_number: String?,

    val fax_number: String?,

    val school_email: String?,

    val website: String?,

    val finalgrades: String?,

    val totalStudents: Long?
)

data class SatScores(
    val dbn: String,

    val num_of_sat_test_takers: String?,

    val sat_critical_reading_avg_score: String?,

    val sat_math_avg_score: String?,

    val sat_writing_avg_score: String?
)

// Merge schools and sat scores data
fun mergeResults(nycSchoolInfo: List<NycSchoolInfo>, satScores: List<SatScores>): List<NycSchool> {

    val schoolsById: Map<String, NycSchoolInfo> = nycSchoolInfo.associateBy { it.dbn }

    val results  = satScores
        .filter { schoolsById[it.dbn] != null }
        .mapNotNull { satScore ->
            schoolsById[satScore.dbn]?.let { nycSchool ->
                nycSchoolInfo.minus(nycSchool)
                toNycSchool(nycSchool, satScore)
            }
        }

    if(nycSchoolInfo.isNotEmpty()) {
        nycSchoolInfo.forEach {
            results.plus(toNycSchool(it, null))
        }
    }
    return results
}

fun toNycSchool(nycSchool: NycSchoolInfo, satScore: SatScores?): NycSchool = NycSchool(
    schoolId = nycSchool.dbn!!,
    name = nycSchool.school_name.orEmpty(),
    overview = nycSchool.overview_paragraph.orEmpty(),
    neighborhood = nycSchool.neighborhood.orEmpty(),
    location = nycSchool.location?.prettifyLocation.orEmpty(),
    email = nycSchool.school_email.orEmpty(),
    phone = nycSchool.phone_number.orEmpty(),
    fax = nycSchool.fax_number.orEmpty(),
    website = nycSchool.website.orEmpty(),
    grades = nycSchool.finalgrades.orEmpty(),
    totalStudents = nycSchool.totalStudents ?: 0,
    totalTestTakers = satScore?.num_of_sat_test_takers.orEmpty(),
    criticalReadingScore = satScore?.sat_critical_reading_avg_score.orEmpty(),
    satMathScore = satScore?.sat_math_avg_score.orEmpty(),
    satWritingScore = satScore?.sat_writing_avg_score.orEmpty()
)

// TODO removing lat long for pretifying the representation
//  can be moved to another column
private val String.prettifyLocation
        get() = run {
            val index = indexOf("(")
            dropLast(length - index)
        }