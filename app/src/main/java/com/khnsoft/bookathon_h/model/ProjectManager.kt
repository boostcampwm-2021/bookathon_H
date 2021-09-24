package com.khnsoft.bookathon_h.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khnsoft.bookathon_h.adapter.statistics.GithubRepoItem
import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.dto.Task
import com.khnsoft.bookathon_h.dto.Time
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
    private val mutableFlattenProjectList = mutableListOf<GithubRepoItem>()
    private val _flattenProjectList: MutableLiveData<List<GithubRepoItem>> by lazy {
        MutableLiveData<List<GithubRepoItem>>()
    }
    val flattenProjectList: LiveData<List<GithubRepoItem>>
        get() = _flattenProjectList

    operator fun get(name: String) = projectList.firstOrNull { it.name == name }

    fun addProject(name: String) {
        if (projectList.firstOrNull { it.name == name } == null) {
            projectList.add(Project(name))
            setFlattenProjectList()
        }
    }

    fun removeProject(project: Project) {
        projectList.remove(project)
        setFlattenProjectList()
    }

    fun addTask(projectName: String, name: String) {
        val project = projectList.firstOrNull { it.name == projectName } ?: return
        project.taskList.add(Task(name))
        setFlattenProjectList()
    }

    fun setFlattenProjectList() {
        mutableFlattenProjectList.clear()
        for (project in projectList) {
            val projectItem = GithubRepoItem.ProjectItem(project)
            mutableFlattenProjectList.add(projectItem)
            Log.d("manager : project", "${projectItem.isChecked}")
            for (task in projectItem.project.taskList) {
                val taskItem = GithubRepoItem.TaskItem(task)
                projectItem.taskList.add(taskItem)
                mutableFlattenProjectList.add(taskItem)
                Log.d("manager : task", "${taskItem.isChecked}")
            }
        }
        _flattenProjectList.value = mutableFlattenProjectList
    }

    fun getTotalProjectTime(): Time {
        var totalProjectTime = Time()
        mutableFlattenProjectList.forEach {
            if (it is GithubRepoItem.ProjectItem && it.isChecked) {
                totalProjectTime += it.projectTime
            }
        }
        return totalProjectTime
    }

    fun getAverageProjectTime(): Time {
        var count = 0
        mutableFlattenProjectList.forEach {
            if (it is GithubRepoItem.ProjectItem && it.isChecked) {
                count++
            }
        }
        return if (count == 0) Time()
        else getTotalProjectTime() / count
    }

    fun getTotalTaskTime(): Time {
        var totalTaskTime = Time()
        mutableFlattenProjectList.forEach {
            if (it is GithubRepoItem.TaskItem && it.isChecked) {
                totalTaskTime += it.task.time
            }
        }
        return totalTaskTime
    }

    fun getAverageTaskTime(): Time {
        var count = 0
        mutableFlattenProjectList.forEach {
            if (it is GithubRepoItem.TaskItem && it.isChecked) {
                count++
            }
        }
        return if (count == 0) Time()
        else getTotalTaskTime() / count
    }
}