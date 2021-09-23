package com.khnsoft.bookathon_h.repository

interface GithubRepository {
    fun getRepositoryNamesOf(username: String): List<String>
}