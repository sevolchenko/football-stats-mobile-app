package ru.vsu.mobile.footballstats.api.model.player

data class PlayerResponse (
    val player: Player,
    val statistics: List<Statistics>
)