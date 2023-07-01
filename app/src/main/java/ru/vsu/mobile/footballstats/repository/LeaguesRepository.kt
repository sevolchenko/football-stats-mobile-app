package ru.vsu.mobile.footballstats.repository

import retrofit2.Response
import ru.vsu.mobile.footballstats.api.model.ApiResponse
import ru.vsu.mobile.footballstats.api.model.league.LeagueResponse
import ru.vsu.mobile.footballstats.api.service.LeaguesApi

class LeaguesRepository(
    private val leaguesApi: LeaguesApi
) {
    suspend fun getAllLeagues(): Response<ApiResponse<List<LeagueResponse>>> {
        return leaguesApi.getAllLeagues()
    }

    suspend fun getLeagueById(id: Int): Response<ApiResponse<List<LeagueResponse>>> {
        return leaguesApi.getLeagueById(id = id)
    }
}