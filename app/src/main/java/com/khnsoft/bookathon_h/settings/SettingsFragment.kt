package com.khnsoft.bookathon_h.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.adapter.GithubProjectAdapter
import com.khnsoft.bookathon_h.databinding.FragmentSettingsBinding
import com.khnsoft.bookathon_h.repository.GithubRepository
import com.khnsoft.bookathon_h.repository.GithubRepositoryImpl
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel
import kotlinx.coroutines.*

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
            println(viewModel.manager.projectList)
            AlertDialog.Builder(activity).apply {
                setTitle(R.string.github_username)
                val usernameView = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_github_username, view as ViewGroup, false)
                setView(usernameView)
                setNegativeButton(R.string.cancel) { _, _ -> }
                setPositiveButton(R.string.confirm) { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val githubProjectList = githubRepository.getRepositoryNamesOf(
                                usernameView.findViewById<EditText>(R.id.usernameEditText).text.toString()
                            ).filter { viewModel.manager[it.name] == null }
                            withContext(Dispatchers.Main) {
                                AlertDialog.Builder(activity).apply {
                                    val projectsView = LayoutInflater.from(activity)
                                        .inflate(R.layout.dialog_github_project_list, usernameView as ViewGroup, false)
                                    val projectRecyclerView =
                                        projectsView.findViewById<RecyclerView>(R.id.projectRecyclerView)

                                    projectRecyclerView.layoutManager = LinearLayoutManager(activity)
                                    projectRecyclerView.adapter = GithubProjectAdapter(githubProjectList)

                                    setView(projectsView)
                                    setNegativeButton(R.string.cancel) { _, _ -> }
                                    setPositiveButton(R.string.confirm) { _, _ ->
                                        githubProjectList.forEach {
                                            if (it.checked) viewModel.manager.addProject(it.name)
                                        }
                                    }
                                    show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            withContext(Dispatchers.Main) {
                                AlertDialog.Builder(activity).apply {
                                    setTitle(R.string.invalid_username)
                                    setPositiveButton(R.string.confirm) { _, _ -> }
                                    show()
                                }
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