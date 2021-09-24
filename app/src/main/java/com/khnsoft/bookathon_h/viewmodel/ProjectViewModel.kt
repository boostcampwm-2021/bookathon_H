package com.khnsoft.bookathon_h.viewmodel

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.khnsoft.bookathon_h.Notifier
import com.khnsoft.bookathon_h.StopWatch
import com.khnsoft.bookathon_h.StopWatchState
import com.khnsoft.bookathon_h.model.ProjectManager
import com.khnsoft.bookathon_h.repository.SharedPreferencesProjectRepository
import com.khnsoft.bookathon_h.view.CircleView
import com.khnsoft.bookathon_h.view.PauseOrResumeButton
import com.khnsoft.bookathon_h.view.StartOrResetButton

class ProjectViewModel: ViewModel() {
    private val manager = ProjectManager(SharedPreferencesProjectRepository)
    lateinit var countDownView: TextView
    lateinit var circleView: CircleView
    lateinit var startOrResetButton: StartOrResetButton
    lateinit var pauseOrResumeButton: PauseOrResumeButton
    lateinit var stopWatch: StopWatch
    lateinit var notifier: Notifier
    var interval = 30*60*1000L/60
    var stopWatchState = StopWatchState.Stopped
    var millisRemaining = 0L
}