package ru.vsu.mobile.footballstats.repository

import retrofit2.Response
import ru.csf.volchenko.footballstats.api.model.team.TeamResponse
import ru.vsu.mobile.footballstats.api.model.ApiResponse
import ru.vsu.mobile.footballstats.api.model.player.PlayerResponse
import ru.vsu.mobile.footballstats.api.service.StatsApi

class StatsRepository(
    private val statsApi: StatsApi
) {
    suspend fun getTopScores(season: Int, league: Int): Response<ApiResponse<List<PlayerResponse>>> {
        return statsApi.getTopScorers(season = season, league = league)
    }

    suspend fun getTopAssists(season: Int, league: Int): Response<ApiResponse<List<PlayerResponse>>> {
        return statsApi.getTopAssists(season = season, league = league)
    }

    suspend fun getTeamsBySeasonLeague(season: Int, league: Int): Response<ApiResponse<List<TeamResponse>>> {
        return statsApi.getTeamsBySeasonLeague(season = season, league = league)
    }
}