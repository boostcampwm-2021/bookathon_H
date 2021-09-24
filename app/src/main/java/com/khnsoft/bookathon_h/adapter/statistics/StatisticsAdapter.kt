package com.khnsoft.bookathon_h.adapter.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.databinding.StatisticsProjectItemBinding
import com.khnsoft.bookathon_h.databinding.StatisticsTaskItemBinding

class StatisticsAdapter : ListAdapter<GithubRepoItem, RecyclerView.ViewHolder>(DiffUtil()) {

    inner class ProjectViewHolder(val binding: StatisticsProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepoItem.ProjectItem) {
            var flag = true
            for (task in item.taskList) {
                flag = flag && task.isChecked
            }
            item.isChecked = flag
            with(binding) {
                projectCheckbox.isChecked = item.isChecked
                projectCheckbox.text = item.project.name
                timeTextView.text = item.projectTime.toString()
            }
            binding.projectCheckbox.setOnClickListener {
                val isChecked = !(it as CheckBox).isChecked
                item.isChecked = isChecked
                for (task in item.taskList) {
                    task.isChecked = isChecked
                }
                notifyDataSetChanged()
            }
//            binding.projectCheckbox.setOnCheckedChangeListener { _, isChecked ->
//                item.isChecked = isChecked
//                for (task in item.taskList) {
//                    task.isChecked = isChecked
//                }
//                notifyDataSetChanged()
//            }
        }
    }

    inner class TaskViewHolder(val binding: StatisticsTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepoItem.TaskItem) {
            with(binding) {
                taskCheckbox.isChecked = item.isChecked
                taskCheckbox.text = item.task.name
                timeTextView.text = item.task.time.toString()
            }
            binding.taskCheckbox.setOnClickListener {
                item.isChecked = !(it as CheckBox).isChecked
            }
//            binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
//                item.isChecked = isChecked
//            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GithubRepoItem.ProjectItem -> 0
            is GithubRepoItem.TaskItem -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> ProjectViewHolder(
                StatisticsProjectItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> TaskViewHolder(
                StatisticsTaskItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is GithubRepoItem.ProjectItem -> {
                (holder as ProjectViewHolder).bind(item)
            }
            is GithubRepoItem.TaskItem -> {
                (holder as TaskViewHolder).bind(item)
            }
        }
    }
}

class DiffUtil : DiffUtil.ItemCallback<GithubRepoItem>() {
    override fun areItemsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem): Boolean {
        return when (oldItem) {
            is GithubRepoItem.ProjectItem -> oldItem == (newItem as GithubRepoItem.ProjectItem)
            is GithubRepoItem.TaskItem -> oldItem == (newItem as GithubRepoItem.TaskItem)
        }
    }

    override fun areContentsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem): Boolean {
        return when (oldItem) {
            is GithubRepoItem.ProjectItem -> oldItem.project.name == (newItem as GithubRepoItem.ProjectItem).project.name
            is GithubRepoItem.TaskItem -> oldItem.task.name == (newItem as GithubRepoItem.TaskItem).task.name
        }
    }
}