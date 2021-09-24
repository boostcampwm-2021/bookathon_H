package com.khnsoft.bookathon_h

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khnsoft.bookathon_h.databinding.TimerFragmentBinding
import com.khnsoft.bookathon_h.view.StartOrResetButton
import com.khnsoft.bookathon_h.view.CircleView
import com.khnsoft.bookathon_h.view.PauseOrResumeButton
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class StopWatchFragment: Fragment() {
    private val viewModel: ProjectViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ProjectViewModel(context) as T
        }
    }

    private lateinit var binding: TimerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timer_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TimerFragmentBinding.bind(view)

        setViewModel()
    }

    private fun setViewModel() {
        viewModel.apply {
            countDownView = binding.textViewCountDown

            startOrResetButton = StartOrResetButton(requireActivity())
            pauseOrResumeButton = PauseOrResumeButton(requireActivity())

            pauseOrResumeButton.setViewModel(viewModel)
            startOrResetButton.setViewModel(viewModel)
                .setViewGroup(binding.linearLayout)

            circleView = CircleView(requireContext())

            binding.frameLayout.addView(viewModel.circleView)
            binding.linearLayout.addView(viewModel.startOrResetButton)

            stopWatch = StopWatch(viewModel.interval, viewModel)

            notifier = Notifier(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }
}