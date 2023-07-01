package ru.vsu.mobile.footballstats.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.vsu.mobile.footballstats.api.model.ApiResponse
import ru.vsu.mobile.footballstats.api.model.league.LeagueResponse

interface LeaguesApi {
    @Headers("Content-type: application/json")
    @GET("/leagues")
    suspend fun getAllLeagues(
    ) : Response<ApiResponse<List<LeagueResponse>>>

    @Headers("Content-type: application/json")
    @GET("/leagues")
    suspend fun getLeagueById(
        @Query("id") id: Int
    ) : Response<ApiResponse<List<LeagueResponse>>>

    companion object {
        fun getLeaguesApi() : LeaguesApi? {
            return ClientApi.client?.create(LeaguesApi::class.java)
        }
    }
}