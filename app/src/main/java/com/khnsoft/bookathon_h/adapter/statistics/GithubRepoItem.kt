package com.khnsoft.bookathon_h.adapter.statistics

import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.dto.Task
import com.khnsoft.bookathon_h.dto.Time

sealed class GithubRepoItem {
    data class ProjectItem(
        val project: Project,
        override var isChecked: Boolean = false,
        val taskList: MutableList<TaskItem> = mutableListOf()
    ) : GithubRepoItem() {
        val projectTime: Time get() = project.taskList.fold(Time()) { old, new -> old + new.time }
    }

    data class TaskItem(val task: Task, override var isChecked: Boolean = false) : GithubRepoItem()

    abstract var isChecked: Boolean
}