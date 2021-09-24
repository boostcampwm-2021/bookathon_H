package com.khnsoft.bookathon_h

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.khnsoft.bookathon_h.databinding.StatisticsProjectItemBinding
import com.khnsoft.bookathon_h.databinding.StatisticsTaskItemBinding
import com.khnsoft.bookathon_h.dto.GithubRepo

class StatisticsAdapter : ListAdapter<GithubRepo, RecyclerView.ViewHolder>(DiffUtil()) {

    inner class ProjectViewHolder(val binding: StatisticsProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepo.Project) {
            var flag = true
            for (task in item.taskList) {
                flag = flag && task.isChecked
            }
            item.isChecked = flag
            with(binding) {
                projectCheckbox.isChecked = item.isChecked
                projectCheckbox.text = item.name
                timeTextView.text = item.getProjectTime().toString()
            }
            binding.projectCheckbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                for (task in item.taskList) {
                    task.isChecked = isChecked
                }
                notifyDataSetChanged()
            }
        }
    }

    inner class TaskViewHolder(val binding: StatisticsTaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepo.Task) {
            with(binding) {
                taskCheckbox.isChecked = item.isChecked
                taskCheckbox.text = item.name
                timeTextView.text = item.time.toString()
            }
            binding.taskCheckbox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GithubRepo.Project -> 0
            is GithubRepo.Task -> 1
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
            is GithubRepo.Project -> {
                (holder as ProjectViewHolder).bind(item)
            }
            is GithubRepo.Task -> {
                (holder as TaskViewHolder).bind(item)
            }
        }
    }
}

class DiffUtil : DiffUtil.ItemCallback<GithubRepo>() {
    override fun areItemsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return when (oldItem) {
            is GithubRepo.Project -> oldItem.name == (newItem as GithubRepo.Project).name
            is GithubRepo.Task -> oldItem.name == (newItem as GithubRepo.Task).name
        }
    }

    override fun areContentsTheSame(oldItem: GithubRepo, newItem: GithubRepo): Boolean {
        return when (oldItem) {
            is GithubRepo.Project -> oldItem == (newItem as GithubRepo.Project)
            is GithubRepo.Task -> oldItem == (newItem as GithubRepo.Task)
        }
    }
}