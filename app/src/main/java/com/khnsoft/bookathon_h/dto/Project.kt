package com.khnsoft.bookathon_h.dto

import kotlinx.serialization.Serializable

@Serializable
class Project(private var _name: String, val taskList: MutableList<Task> = mutableListOf(Task("default"))) {
    val name get() = _name

    operator fun get(name: String) = taskList.firstOrNull { it.name == name }

    fun addTask(name: String) {
        taskList.add(Task(name))
    }

    fun setTimeOf(task: Task, time: Time) {
        task.setTime(time)
    }

    fun renameTask(task: Task, name: String) {
        task.rename(name)
    }

    fun removeTask(task: Task) {
        taskList.remove(task)
    }

    fun rename(name: String) {
        _name = name
    }

    override fun toString(): String = "Project(name=${name}, taskList=${taskList})"
}