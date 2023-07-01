package ru.vsu.mobile.footballstats.api.model.league

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class League(
    val id: Int,
    val name: String,
    val logo: String
) : Parcelable
