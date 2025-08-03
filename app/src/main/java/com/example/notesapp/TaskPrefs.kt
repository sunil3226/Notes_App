package com.example.notesapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TaskPrefs {

    private const val PREF_NAME = "tasks_pref"
    private const val KEY_TASKS = "tasks_list"

    fun saveTasks(context: Context, list: ArrayList<Tasks>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = Gson().toJson(list)
        editor.putString(KEY_TASKS, json)
        editor.apply()
    }

    fun loadTasks(context: Context): ArrayList<Tasks> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_TASKS, null)
        return if (json != null) {
            val type = object : TypeToken<ArrayList<Tasks>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }
    }
}
