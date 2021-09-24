package com.khnsoft.bookathon_h.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.dto.Task
import com.khnsoft.bookathon_h.model.ProjectManager
import com.khnsoft.bookathon_h.repository.SharedPreferencesProjectRepository

class ProjectViewModel(private val context: Context?): ViewModel() {
    private val manager = ProjectManager(SharedPreferencesProjectRepository)
    private val _projectList: MutableLiveData<List<Project>>
    val projectList: LiveData<List<Project>> get() = _projectList

    init {
        manager.load(context)
        _projectList = MutableLiveData(manager.projectList.toList())
    }

    fun addProject(name: String) {
        manager.addProject(name)
        notifyChange()
    }

    fun renameProject(project: Project, name: String) {
        project.rename(name)
        notifyChange()
    }

    fun removeProject(project: Project) {
        manager.removeProject(project)
        notifyChange()
    }

    fun addTaskTo(project: Project, name: String) {
        project.addTask(name)
        notifyChange()
    }

    fun renameTask(task: Task, name: String) {
        task.rename(name)
        notifyChange()
    }

    fun removeTask(project: Project, task: Task) {
        project.removeTask(task)
        notifyChange()
    }

    fun containsProject(name: String): Boolean = manager[name] != null

    private fun notifyChange() {
        _projectList.value = manager.projectList
        manager.save(context)
    }
}