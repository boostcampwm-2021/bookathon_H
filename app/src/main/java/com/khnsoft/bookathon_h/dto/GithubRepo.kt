package com.khnsoft.bookathon_h.dto

import kotlinx.serialization.Serializable

sealed class GithubRepo {
    @Serializable
    data class Project(
        private var _name: String,
        val taskList: MutableList<Task> = mutableListOf(Task("default")),
        var isChecked: Boolean = false
    ) : GithubRepo() {
        val name = _name

        operator fun get(name: String) = taskList.firstOrNull { it.name == name }

        fun setTimeOf(task: Task, time: Time) {
            replaceTask(task, task.copy(time = time))
        }

        fun renameTask(task: Task, name: String) {
            replaceTask(task, task.copy(name = name))
        }

        fun removeTask(task: Task) {
            taskList.remove(task)
        }

        fun rename(name: String) {
            _name = name
        }

        private fun replaceTask(old: Task, new: Task) {
            val idx = taskList.indexOf(old)
            taskList.removeAt(idx)
            taskList.add(idx, new)
        }

        fun getProjectTime(): Time {
            var totalTime = Time()
            taskList.forEach { task ->
                totalTime += task.time
            }
            return totalTime
        }

        override fun toString(): String = "Project(name=${name}, taskList=${taskList})"
    }

    @Serializable
    data class Task(val name: String, val time: Time = Time(), var isChecked: Boolean = false) :
        GithubRepo()
}
