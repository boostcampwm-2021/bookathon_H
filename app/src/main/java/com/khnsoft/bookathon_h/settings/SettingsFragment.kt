package com.khnsoft.bookathon_h.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.databinding.FragmentSettingsBinding
import com.khnsoft.bookathon_h.repository.GithubRepository
import com.khnsoft.bookathon_h.repository.GithubRepositoryImpl
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private val viewModel: ProjectViewModel by activityViewModels()
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val _temporaryHour = 0
    private val _temporaryMinute = 30

    private val githubRepository: GithubRepository = GithubRepositoryImpl

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

        binding.githubBtnTextView.setOnClickListener {
            AlertDialog.Builder(activity).apply {
                setTitle(R.string.github_username)
                val view = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_github_username, view as ViewGroup, false)
                setView(view)
                setNegativeButton(R.string.cancel) { _, _ -> }
                setPositiveButton(R.string.confirm) { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val projectNames = githubRepository.getRepositoryNamesOf(
                                view.findViewById<EditText>(R.id.usernameEditText).text.toString()
                            )
                            println(projectNames)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            AlertDialog.Builder(activity).apply {
                                setTitle(R.string.invalid_username)
                                setPositiveButton(R.string.confirm) { _, _ -> }
                                show()
                            }
                        }
                    }
                }
                show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}