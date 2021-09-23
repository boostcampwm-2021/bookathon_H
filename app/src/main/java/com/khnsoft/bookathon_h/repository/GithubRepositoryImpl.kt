package com.khnsoft.bookathon_h.repository

import com.khnsoft.bookathon_h.dto.GithubProject
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets.UTF_8
import java.util.zip.GZIPInputStream

object GithubRepositoryImpl : GithubRepository {
    override fun getRepositoryNamesOf(username: String): List<String> {
        val url = URL("https://api.github.com/users/${username}/repos")
        val conn = url.openConnection() as HttpURLConnection
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json")

        val bytes = conn.inputStream.readBytes()
        val response = if (conn.contentEncoding == "gzip")
            GZIPInputStream(bytes.inputStream()).bufferedReader(UTF_8)
                .use { it.readText() }
        else bytes.inputStream().bufferedReader(UTF_8).use { it.readText() }

        if (response[0] != '[') throw Exception("Invalid response for username \"${username}\".")

        return Json { ignoreUnknownKeys = true }.decodeFromString<List<GithubProject>>(response).map { it.name }
    }
}