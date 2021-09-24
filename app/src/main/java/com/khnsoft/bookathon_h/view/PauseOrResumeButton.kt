package com.khnsoft.bookathon_h.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.StopWatch
import com.khnsoft.bookathon_h.StopWatchState
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class PauseOrResumeButton: androidx.appcompat.widget.AppCompatButton {
    private lateinit var viewModel: ProjectViewModel

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context)

    init {
        text = "PAUSE"
        background = resources.getDrawable(R.drawable.button_right)
    }

    fun setViewModel(viewmodel: ProjectViewModel) {
        this.viewModel = viewmodel
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (viewModel.stopWatchState == StopWatchState.Paused) {
                        viewModel.stopWatch = StopWatch(viewModel.millisRemaining, viewModel)
                        if (viewModel.stopWatch.resume()) {
                            text = "PAUSE"
                            viewModel.stopWatchState = StopWatchState.Running
                        }
                    } else if (viewModel.stopWatchState == StopWatchState.Running) {
                        if (viewModel.stopWatch.pause()) {
                            text = "RESUME"
                            viewModel.stopWatchState = StopWatchState.Paused
                        }
                    }
                }
            }
        }
        return true
    }
}
