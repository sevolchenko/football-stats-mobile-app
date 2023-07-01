package ru.vsu.mobile.footballstats.api.model.league

data class LeagueResponse(
    val league: League,
    val country: Country,
    val seasons: List<Season>
)
