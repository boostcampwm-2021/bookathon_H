package com.khnsoft.bookathon_h.dto

import kotlinx.serialization.Serializable

@Serializable
data class Time(val hours: Int = 0, val minutes: Int = 0, val seconds: Int = 0) {
    companion object {
        fun fromSeconds(seconds: Int) =
            Time(seconds / 3600, (seconds % 3600) / 60, seconds % 60)
    }

    operator fun plus(other: Time) =
        fromSeconds((hours + other.hours) * 3600 + (minutes + other.minutes) * 60 + (seconds + other.seconds))

    operator fun div(n: Int) = fromSeconds(((hours * 3600 + minutes * 60 + seconds) / n))

    override fun toString(): String = "$hours : $minutes : $seconds"
}