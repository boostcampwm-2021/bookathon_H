package com.khnsoft.bookathon_h.settings

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.OnEditTextConfirmListener
import com.khnsoft.bookathon_h.R
import com.khnsoft.bookathon_h.adapter.GithubProjectAdapter
import com.khnsoft.bookathon_h.adapter.SettingsItem
import com.khnsoft.bookathon_h.adapter.SettingsItemAdapter
import com.khnsoft.bookathon_h.databinding.FragmentSettingsBinding
import com.khnsoft.bookathon_h.repository.GithubRepository
import com.khnsoft.bookathon_h.repository.GithubRepositoryImpl
import com.khnsoft.bookathon_h.viewmodel.ProjectViewModel
import kotlinx.coroutines.*

class SettingsFragment : Fragment() {
    private val viewModel: ProjectViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ProjectViewModel(context) as T
        }
    }
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val _temporaryHour = 0
    private val _temporaryMinute = 30

    private val githubRepository: GithubRepository = GithubRepositoryImpl
    private val settingsItemAdapter = SettingsItemAdapter()

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

        viewModel.projectList.observe(viewLifecycleOwner) { projectList ->
            val settingsItems = mutableListOf<SettingsItem>()
            projectList.forEach { project ->
                settingsItems.add(SettingsItem.ProjectItem(project) {
                    AlertDialog.Builder(activity).apply {
                        setItems(R.array.projectOption) { _, i ->
                            when (i) {
                                0 -> showEditTextDialog(R.string.rename_project, project.name, R.string.project_name) {
                                    viewModel.renameProject(project, it)
                                }
                                1 -> showEditTextDialog(R.string.add_task, hintId = R.string.task_name) {
                                    viewModel.addTaskTo(project, it)
                                }
                                2 -> viewModel.removeProject(project)
                            }
                        }
                        show()
                    }
                })
                settingsItems.addAll(project.taskList.map { task ->
                    SettingsItem.TaskItem(task) {
                        AlertDialog.Builder(activity).apply {
                            setItems(R.array.taskOption) { _, i ->
                                when (i) {
                                    0 -> showEditTextDialog(R.string.rename_task, task.name, R.string.task_name) {
                                        viewModel.renameTask(task, it)
                                    }
                                    1 -> viewModel.removeTask(project, task)
                                }
                            }
                            show()
                        }
                    }
                })
            }
            settingsItems.add(SettingsItem.AddItem {
                showEditTextDialog(R.string.add_project, hintId = R.string.project_name) {
                    viewModel.addProject(it)
                }
            })
            settingsItemAdapter.submitList(settingsItems)
        }

        binding.githubBtnTextView.setOnClickListener {
            showEditTextDialog(R.string.github_username, hintId = R.string.username) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val githubProjectList = githubRepository.getRepositoryNamesOf(it).filter {
                            !viewModel.containsProject(it.name)
                        }
                        withContext(Dispatchers.Main) {
                            AlertDialog.Builder(activity).apply {
                                val projectsView = LayoutInflater.from(activity)
                                    .inflate(R.layout.dialog_github_project_list, view as ViewGroup, false)
                                val projectRecyclerView =
                                    projectsView.findViewById<RecyclerView>(R.id.projectRecyclerView)

                                projectRecyclerView.layoutManager = LinearLayoutManager(activity)
                                projectRecyclerView.adapter = GithubProjectAdapter(githubProjectList)

                                setView(projectsView)
                                setNegativeButton(R.string.cancel) { _, _ -> }
                                setPositiveButton(R.string.confirm) { _, _ ->
                                    githubProjectList.forEach {
                                        if (it.checked) viewModel.addProject(it.name)
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
        }

        binding.projectRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.projectRecyclerView.adapter = settingsItemAdapter

        return binding.root
    }

    private fun showEditTextDialog(
        titleId: Int,
        defaultText: String? = null,
        hintId: Int? = null,
        listener: OnEditTextConfirmListener? = null
    ) {
        AlertDialog.Builder(activity).apply {
            setTitle(titleId)
            val editTextView = LayoutInflater.from(activity)
                .inflate(R.layout.dialog_edittext, view as ViewGroup, false)
            val editText = editTextView.findViewById<EditText>(R.id.editText)
            hintId?.let { editText.setHint(hintId) }
            defaultText?.let { editText.setText(defaultText) }
            setView(editTextView)
            setNegativeButton(R.string.cancel) { _, _ -> }
            setPositiveButton(R.string.confirm) { _, _ ->
                listener?.onConfirm(editText.text.toString())
            }
            show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}