package com.khnsoft.bookathon_h

import android.os.CountDownTimer
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class StopWatch(
    private val millisInFuture: Long,
    private var viewModel: ProjectViewModel
) : CountDownTimer(millisInFuture, 1000) {

    private var millisRemaining = 0L

    override fun onTick(millisUntilFinished: Long) {
        millisRemaining = millisUntilFinished
        var minute = (millisRemaining / 1000 / 60).toString()
        var second = ((millisRemaining / 1000) % 60).toString()
        if (minute.length < 2) minute = "0$minute"
        if (second.length < 2) second = "0$second"
        viewModel.countDownView.setText("$minute:$second")
        viewModel.millisRemaining = millisRemaining
    }

    override fun onFinish() {
        // Notification
        viewModel.notifier.giveNotification()
        viewModel.startOrResetButton.text = "START"
        viewModel.stopWatchState = StopWatchState.Stopped
        viewModel.startOrResetButton.viewGroup.removeView(viewModel.pauseOrResumeButton)
        viewModel.circleView.stop()
//        viewModel.startOrResetButton.background = getDrawable(context, R.drawable.button)
        stop()
    }

    fun begin(): Boolean {
        if (viewModel.stopWatchState != StopWatchState.Stopped) {
            return false
        }

        start()
        viewModel.circleView.start()

        viewModel.stopWatchState = StopWatchState.Running
        return true
    }

    fun pause(): Boolean {
        if (viewModel.stopWatchState != StopWatchState.Running) {
            return false
        }

        cancel()
        viewModel.circleView.pause()


        viewModel.stopWatchState = StopWatchState.Paused
        return true
    }

    fun resume(): Boolean {
        if (viewModel.stopWatchState != StopWatchState.Paused) {
            return false
        }

        start()
        viewModel.circleView.resume()

        viewModel.stopWatchState = StopWatchState.Running
        return true
    }

    fun stop(): Boolean {
        if (viewModel.stopWatchState != StopWatchState.Running) {
            return false
        }

        cancel()
        viewModel.circleView.stop()
        viewModel.countDownView.setText("00:00")

        viewModel.stopWatchState = StopWatchState.Stopped
        return true
    }

    fun accumulateTimeToTask() {

    }
}