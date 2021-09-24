package com.khnsoft.bookathon_h.adapter.settings

import androidx.recyclerview.widget.DiffUtil

class SettingsItemDiffCallback: DiffUtil.ItemCallback<SettingsItem>() {
    override fun areItemsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
        return when(oldItem) {
            is SettingsItem.ProjectItem -> oldItem.project.name == (newItem as? SettingsItem.ProjectItem)?.project?.name
            is SettingsItem.TaskItem -> oldItem.task.name == (newItem as? SettingsItem.TaskItem)?.task?.name
            is SettingsItem.AddItem -> oldItem == newItem
        }
    }
}