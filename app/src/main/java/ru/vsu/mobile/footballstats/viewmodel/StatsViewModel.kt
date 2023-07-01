package ru.vsu.mobile.footballstats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.mobile.footballstats.api.model.InfoItem
import ru.vsu.mobile.footballstats.api.model.league.LeagueResponse
import ru.vsu.mobile.footballstats.repository.LeaguesRepository
import ru.vsu.mobile.footballstats.repository.StatsRepository

class StatsViewModel(private val statsRepository: StatsRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val infoList = MutableLiveData<List<InfoItem>>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllTopScorers(season: Int, league: Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = statsRepository.getTopScores(season = season, league = league)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val resList = body.response.map {
                            InfoItem(
                                id = it.player.id,
                                name = it.player.name,
                                pic = it.player.photo,
                                score = it.statistics[0].goals.total
                            )
                        }.sortedByDescending { it.score }
                        infoList.postValue(resList)
                        loading.value = false
                    }
                } else {
                    onError("Ошибка : ${response.message()} ")
                }
            }
        }
    }

    fun getAllTopAssists(season: Int, league: Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = statsRepository.getTopAssists(season = season, league = league)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val resList = body.response.map {
                            InfoItem(
                                id = it.player.id,
                                name = it.player.name,
                                pic = it.player.photo,
                                score = it.statistics[0].goals.assists
                            )
                        }.sortedByDescending { it.score }
                        infoList.postValue(resList)
                        loading.value = false
                    }
                } else {
                    onError("Ошибка : ${response.message()} ")
                }
            }
        }
    }

    fun getAllTeams(season: Int, league: Int) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = statsRepository.getTeamsBySeasonLeague(season = season, league = league)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        infoList.postValue(body.response.map {
                            InfoItem(
                                id = it.team.id,
                                name = it.team.name,
                                pic = it.team.logo,
                                score = -1
                            )
                        })
                        loading.value = false
                    }
                } else {
                    onError("Ошибка : ${response.message()} ")
                }
            }
        }
    }


    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}

