package com.example.notesapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tasksList = ArrayList<Tasks>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, 0, 0, systemInsets.bottom)
            insets
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        tasksList = TaskPrefs.loadTasks(this)

        val adapter = TaskAdapter(tasksList) { updatedTasks ->
            TaskPrefs.saveTasks(this, updatedTasks)
        }

        binding.tasksList.adapter = adapter

        binding.submitButton.setOnClickListener {
            val newTask = binding.editText.text.toString()
            if (newTask.isNotEmpty() && newTask.length < 50) {
                tasksList.add(Tasks(newTask))
                adapter.notifyItemInserted(tasksList.size - 1)
                TaskPrefs.saveTasks(this, tasksList)
                binding.editText.text?.clear()
            } else {
                Toast.makeText(this, "Enter a valid name!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
