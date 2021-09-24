package com.khnsoft.bookathon_h.model

import android.content.Context
import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.repository.ProjectRepository

class ProjectManager(
    private val repository: ProjectRepository,
    val projectList: MutableList<Project> = mutableListOf()
) {
    fun save(context: Context?) {
        repository.setProjectList(context ?: return, projectList)
    }
    fun load(context: Context?) {
        projectList.clear()
        projectList.addAll(repository.getProjectList(context ?: return))
    }

    operator fun get(name: String) = projectList.firstOrNull { it.name == name }

    fun addProject(name: String) {
        if (projectList.firstOrNull { it.name == name } == null) projectList.add(Project(name))
    }

    fun removeProject(project: Project) {
        projectList.remove(project)
    }
}