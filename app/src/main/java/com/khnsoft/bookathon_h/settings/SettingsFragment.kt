package com.khnsoft.bookathon_h.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.khnsoft.bookathon_h.databinding.FragmentSettingsBinding
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel

class SettingsFragment: Fragment() {
    private val viewModel: ProjectViewModel by activityViewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val _temporaryHour = 0
    private val _temporaryMinute = 30

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.workTimeHourNumberPicker.apply {
            minValue = 0
            maxValue = 99
            value = _temporaryHour
            wrapSelectorWheel = false
        }
        binding.workTimeMinuteNumberPicker.apply {
            minValue = 0
            maxValue = 59
            value = _temporaryMinute
            wrapSelectorWheel = false
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}