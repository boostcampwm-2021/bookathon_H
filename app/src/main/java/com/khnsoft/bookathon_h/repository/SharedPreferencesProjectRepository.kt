package com.khnsoft.bookathon_h.repository

import android.content.Context
import com.khnsoft.bookathon_h.dto.Project
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

object SharedPreferencesProjectRepository: ProjectRepository {
    private const val PREFERENCES_NAME = "BookathonH"

    private fun getPreferences(context: Context) =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun setProjectList(context: Context, projectList: List<Project>) {
        val encoded = Json.encodeToString(projectList)
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString("projects", encoded)
        editor.apply()
    }

    override fun getProjectList(context: Context): List<Project> {
        val prefs = getPreferences(context)
        val encoded = prefs.getString("projects", null) ?: return listOf()
        return Json.decodeFromString(encoded)
    }
}