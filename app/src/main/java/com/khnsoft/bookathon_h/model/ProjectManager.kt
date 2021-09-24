package com.khnsoft.bookathon_h.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khnsoft.bookathon_h.dto.GithubRepo
import com.khnsoft.bookathon_h.dto.Time
import com.khnsoft.bookathon_h.repository.ProjectRepository

class ProjectManager(
    private val repository: ProjectRepository,
    val projectList: MutableList<GithubRepo.Project> = mutableListOf()
) {
    fun save(context: Context?) {
        repository.setProjectList(context ?: return, projectList)
    }
    fun load(context: Context?) {
        projectList.clear()
        projectList.addAll(repository.getProjectList(context ?: return))
    }
    private val mutableFlattenProjectList = mutableListOf<GithubRepo>()
    private val _flattenProjectList: MutableLiveData<List<GithubRepo>> by lazy {
        MutableLiveData<List<GithubRepo>>()
    }
    val flattenProjectList: LiveData<List<GithubRepo>>
        get() = _flattenProjectList

    fun save(context: Context) = repository.setProjectList(context, projectList)
    fun load(context: Context) = repository.getProjectList(context)

    operator fun get(name: String) = projectList.firstOrNull { it.name == name }

    fun addProject(name: String) {
        if (projectList.firstOrNull { it.name == name } == null) {
            projectList.add(GithubRepo.Project(name))
            setFlattenProjectList()
        }
    }

    fun removeProject(project: GithubRepo.Project) {
        projectList.remove(project)
        setFlattenProjectList()
    }

    fun addTask(projectName: String, name: String) {
        val project = projectList.firstOrNull { it.name == projectName } ?: return
        project.taskList.add(GithubRepo.Task(name))
        setFlattenProjectList()
    }

    fun setFlattenProjectList() {
        mutableFlattenProjectList.clear()
        for (project in projectList) {
            mutableFlattenProjectList.add(project)
            Log.d("manager : project", "${project.isChecked}")
            for (task in project.taskList) {
                mutableFlattenProjectList.add(task)
                Log.d("manager : task", "${task.isChecked}")
            }
        }
        _flattenProjectList.value = mutableFlattenProjectList

    }

    fun getTotalProjectTime(): Time {
        var totalProjectTime = Time()
        projectList.forEach {
            if (it.isChecked) {
                totalProjectTime += it.getProjectTime()
            }
        }
        return totalProjectTime
    }

    fun getAverageProjectTime(): Time {
        var count = 0
        projectList.forEach {
            if (it.isChecked) {
                count++
            }
        }
        return if (count == 0) Time()
        else getTotalProjectTime() / count
    }

    fun getTotalTaskTime(): Time {
        var totalTaskTime = Time()
        for (project in projectList) {
            for (task in project.taskList) {
                if (task.isChecked) {
                    totalTaskTime += task.time
                }
            }
        }
        return totalTaskTime
    }

    fun getAverageTaskTime(): Time {
        var count = 0
        for (project in projectList) {
            for (task in project.taskList) {
                if (task.isChecked) {
                    count++
                }
            }
        }
        return if (count == 0) Time()
        else getTotalTaskTime() / count
    }
}