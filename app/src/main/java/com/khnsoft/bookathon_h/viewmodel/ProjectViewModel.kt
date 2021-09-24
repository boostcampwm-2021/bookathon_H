package com.khnsoft.bookathon_h.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.khnsoft.bookathon_h.dto.Project
import com.khnsoft.bookathon_h.dto.Task
import com.khnsoft.bookathon_h.dto.Time
import android.widget.TextView
import com.khnsoft.bookathon_h.Notifier
import com.khnsoft.bookathon_h.StopWatch
import com.khnsoft.bookathon_h.StopWatchState
import com.khnsoft.bookathon_h.model.ProjectManager
import com.khnsoft.bookathon_h.repository.SharedPreferencesProjectRepository
import com.khnsoft.bookathon_h.view.CircleView
import com.khnsoft.bookathon_h.view.PauseOrResumeButton
import com.khnsoft.bookathon_h.view.StartOrResetButton

class ProjectViewModel(private val context: Context?): ViewModel() {
    val manager = ProjectManager(SharedPreferencesProjectRepository)
    private val _projectList: MutableLiveData<List<Project>>
    val projectList: LiveData<List<Project>> get() = _projectList
    private val _defaultTime = MutableLiveData(Time(0, 30))
    val defaultTime: LiveData<Time> get() = _defaultTime

    lateinit var countDownView: TextView
    lateinit var circleView: CircleView
    lateinit var startOrResetButton: StartOrResetButton
    lateinit var pauseOrResumeButton: PauseOrResumeButton
    lateinit var stopWatch: StopWatch
    lateinit var notifier: Notifier
    var interval = 30*60*1000L/60
    var stopWatchState = StopWatchState.Stopped
    var millisRemaining = 0L

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

    fun setHour(hour: Int) {
        _defaultTime.value = _defaultTime.value?.copy(hours = hour)
    }

    fun setMinute(minute: Int) {
        _defaultTime.value = _defaultTime.value?.copy(minutes = minute)
    }

    fun containsProject(name: String): Boolean = manager[name] != null

    private fun notifyChange() {
        _projectList.value = manager.projectList
        manager.save(context)
    }
}