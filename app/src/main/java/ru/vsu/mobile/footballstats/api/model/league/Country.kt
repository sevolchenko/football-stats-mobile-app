package ru.vsu.mobile.footballstats.api.model.league

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response

@Parcelize
data class Country(
    val name: String,
    val code: String,
    val flag: String
) : Parcelable
