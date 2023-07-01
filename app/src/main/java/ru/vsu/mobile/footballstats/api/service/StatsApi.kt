package ru.vsu.mobile.footballstats.api.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.csf.volchenko.footballstats.api.model.team.TeamResponse
import ru.vsu.mobile.footballstats.api.model.ApiResponse
import ru.vsu.mobile.footballstats.api.model.player.PlayerResponse

interface StatsApi {

    @Headers("Content-type: application/json")
    @GET("/players/topscorers")
    suspend fun getTopScorers(
        @Query("season") season: Int,
        @Query("league") league: Int,
    ) : Response<ApiResponse<List<PlayerResponse>>>

    @Headers("Content-type: application/json")
    @GET("/players/topassists")
    suspend fun getTopAssists(
        @Query("season") season: Int,
        @Query("league") league: Int,
    ) : Response<ApiResponse<List<PlayerResponse>>>

    @Headers("Content-type: application/json")
    @GET("/teams")
    suspend fun getTeamsBySeasonLeague(
        @Query("season") season: Int,
        @Query("league") league: Int,
    ) : Response<ApiResponse<List<TeamResponse>>>

    companion object {
        fun getStatsApi() : StatsApi? {
            return ClientApi.client?.create(StatsApi::class.java)
        }
    }
}