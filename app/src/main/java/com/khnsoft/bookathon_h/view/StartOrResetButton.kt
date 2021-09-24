package com.khnsoft.bookathon_h.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.StopWatch
import com.khnsoft.bookathon_h.StopWatchState
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class StartOrResetButton: androidx.appcompat.widget.AppCompatButton {
    private lateinit var viewModel: ProjectViewModel
    lateinit var viewGroup: ViewGroup

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context)

    init {
        text = "START"
        background = resources.getDrawable(R.drawable.button)
    }

    fun setViewModel(viewmodel: ProjectViewModel): StartOrResetButton {
        this.viewModel = viewmodel
        return this
    }

    fun setViewGroup(viewGroup: ViewGroup): StartOrResetButton {
        this.viewGroup = viewGroup
        return this
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (viewModel.stopWatchState == StopWatchState.Stopped) {
                        viewModel.stopWatch = StopWatch(viewModel.interval, viewModel)
                        if (viewModel.stopWatch.begin()) {
                            text = "RESET"
                            viewModel.stopWatchState = StopWatchState.Running
                            viewGroup.addView(viewModel.pauseOrResumeButton)
                            background = resources.getDrawable(R.drawable.button_left)
                        }
                    }
                    else {
                        if (viewModel.stopWatch.stop()) {
                            text = "START"
                            viewModel.stopWatchState = StopWatchState.Stopped
                            viewGroup.removeView(viewModel.pauseOrResumeButton)
                            background = resources.getDrawable(R.drawable.button)
                        }
                    }
                }
            }
        }
        return true
    }
}