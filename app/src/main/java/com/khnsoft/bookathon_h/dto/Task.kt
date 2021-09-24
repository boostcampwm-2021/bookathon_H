package com.khnsoft.bookathon_h.dto

import kotlinx.serialization.Serializable

@Serializable
class Task(private var _name: String, var _time: Time = Time()) {
    val name get() = _name
    val time get() = _time

    fun rename(name: String) {
        _name = name
    }

    fun setTime(time: Time) {
        _time = time
    }
}