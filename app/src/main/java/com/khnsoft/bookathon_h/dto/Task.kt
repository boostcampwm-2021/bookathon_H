package com.khnsoft.bookathon_h.dto

import kotlinx.serialization.Serializable

@Serializable
data class Task(val name: String, val time: Time = Time())