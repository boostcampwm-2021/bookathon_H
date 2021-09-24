//package com.khnsoft.bookathon_h.dto
//
//import kotlinx.serialization.Serializable
//
//@Serializable
//class Project(private var _name: String, val taskList: MutableList<Task> = mutableListOf(Task("default"))) {
//    val name = _name
//
//    operator fun get(name: String) = taskList.firstOrNull { it.name == name }
//
//    fun addTask(name: String) {
//        taskList.add(Task(name))
//    }
//
//    fun setTimeOf(task: Task, time: Time) {
//        replaceTask(task, task.copy(time = time))
//    }
//
//    fun renameTask(task: Task, name: String) {
//        replaceTask(task, task.copy(name = name))
//    }
//
//    fun removeTask(task: Task) {
//        taskList.remove(task)
//    }
//
//    fun rename(name: String) {
//        _name = name
//    }
//
//    private fun replaceTask(old: Task, new: Task) {
//        val idx = taskList.indexOf(old)
//        taskList.removeAt(idx)
//        taskList.add(idx, new)
//    }
//
//    override fun toString(): String = "Project(name=${name}, taskList=${taskList})"
//}