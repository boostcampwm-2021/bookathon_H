package com.khnsoft.bookathon_h.adapter

import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.dto.Task

sealed class SettingsItem {
    data class ProjectItem(val project: Project, val onClickListener: OnItemClickListener): SettingsItem() {
        override fun hashCode(): Int = project.hashCode()
        override fun equals(other: Any?): Boolean = hashCode() == other.hashCode()
    }

    data class TaskItem(val task: Task, val onClickListener: OnItemClickListener): SettingsItem() {
        override fun hashCode(): Int = task.hashCode()
        override fun equals(other: Any?): Boolean = hashCode() == other.hashCode()
    }

    data class AddItem(val onClickListener: OnItemClickListener): SettingsItem()

    fun interface OnItemClickListener {
        fun onClick()
    }
}