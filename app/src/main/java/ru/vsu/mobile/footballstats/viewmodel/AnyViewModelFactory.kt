package ru.vsu.mobile.footballstats.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vsu.mobile.footballstats.repository.LeaguesRepository
import ru.vsu.mobile.footballstats.repository.StatsRepository
import java.lang.IllegalArgumentException

class AnyViewModelFactory(
    private val repository: Any
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LeaguesViewModel::class.java) ->
                LeaguesViewModel(
                    leaguesRepository = repository as LeaguesRepository,
                ) as T
            modelClass.isAssignableFrom(StatsViewModel::class.java) ->
                StatsViewModel(
                    statsRepository = repository as StatsRepository,
                ) as T
            else -> throw IllegalArgumentException("ViewModel not found")
        }
    }
}