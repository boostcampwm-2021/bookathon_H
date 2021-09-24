package com.khnsoft.bookathon_h.repository

import android.content.Context
import com.khnsoft.bookathon_h.dto.Project

interface ProjectRepository {
    fun setProjectList(context: Context, projectList: List<Project>)
    fun getProjectList(context: Context): List<Project>
}