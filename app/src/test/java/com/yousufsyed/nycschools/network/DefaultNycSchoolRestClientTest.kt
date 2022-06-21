package com.yousufsyed.nycschools.network

import com.yousufsyed.nycschools.mockResponse
import com.yousufsyed.nycschools.satScores
import io.kotest.core.spec.style.DescribeSpec
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DefaultNycSchoolRestClientTest : DescribeSpec({

    val mockWebServer = MockWebServer()

    val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NycSchoolsApi::class.java)

    val nycSchoolsRestClient = DefaultNycSchoolRestClient(api)

    describe("When fetching sat scores") {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockResponse)
        )

        val actual = nycSchoolsRestClient.getSatScores()
        val expected = listOf(satScores)

        it("should fetch Sat scores correctly") {
            assertEquals(expected, actual)
        }
    }
})