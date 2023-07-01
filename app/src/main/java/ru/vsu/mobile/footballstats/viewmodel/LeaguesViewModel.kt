package ru.vsu.mobile.footballstats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.vsu.mobile.footballstats.api.model.league.LeagueResponse
import ru.vsu.mobile.footballstats.repository.LeaguesRepository

class LeaguesViewModel(private val leaguesRepository: LeaguesRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val leagueList = MutableLiveData<List<LeagueResponse>>()
    val favLeaguesList = MutableLiveData<List<LeagueResponse>>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllLeagues() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = leaguesRepository.getAllLeagues()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        leagueList.postValue(body.response!!)
                        loading.value = false
                    }
                } else {
                    onError("Ошибка : ${response.message()} ")
                }
            }
        }
    }


    fun getLeaguesByIds(ids: List<Int>) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val temp = mutableListOf<LeagueResponse>()
            for (id in ids) {
                val response = leaguesRepository.getLeagueById(id = id)
                val resp = response.body()!!.response!!
                if (resp.isNotEmpty()) {
                    temp.add(resp[0])
                }
            }
            withContext(Dispatchers.Main) {
                favLeaguesList.postValue(temp)
                loading.value = false
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}

