package com.yousufsyed.nycschools.network

import android.net.Uri
import com.yousufsyed.nycschools.network.data.NycSchoolInfo
import com.yousufsyed.nycschools.network.data.SatScores
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName
import retrofit2.http.Url

const val SCHOOLS_ATTRIBUTE_LIST = "`dbn`,`school_name`,`overview_paragraph`,`neighborhood`,`location`,`phone_number`,`fax_number`,`school_email`,`website`,`finalgrades`,`total_students`"
const val SAT_SCORES_ATTRIBUTE_LIST = "`dbn`,`num_of_sat_test_takers`,`sat_critical_reading_avg_score`,`sat_math_avg_score`,`sat_writing_avg_score`"
const val ORDER_ASC = "`:id`+ASC"

interface NycSchoolsApi {

    @GET("/api/id/s3k6-pzi2.json?\$select=$SCHOOLS_ATTRIBUTE_LIST&\$order=$ORDER_ASC")
    suspend fun getSchools(
        //@Query("\$select", encoded = true) select: String = SCHOOLS_ATTRIBUTE_LIST,
        //@Query("\$order", encoded = true) order: String = ORDER_ASC,
    ): List<NycSchoolInfo>

    @GET("/api/id/f9bf-2cp4.json?\$select=$SAT_SCORES_ATTRIBUTE_LIST")
    suspend fun getSatScores(

    ) : List<SatScores>
}
//@Url url: Uri = Uri.parse("https://data.cityofnewyork.us/id/f9bf-2cp4.json?\$select=$SAT_SCORES_ATTRIBUTE_LIST")
//@Query("\$select", encoded = true) select: String = SAT_SCORES_ATTRIBUTE_LIST,
//@Query("\$order", encoded = true) order: String = ORDER_ASC