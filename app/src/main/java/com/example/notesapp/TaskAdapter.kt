package com.example.notesapp

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: ArrayList<Tasks>,
    private val onTasksUpdated: (ArrayList<Tasks>) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.taskName)
        val editButton: ImageButton = view.findViewById(R.id.editButton)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.name.text = tasks[position].taskName

        holder.deleteButton.setOnClickListener {
            tasks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tasks.size)
            onTasksUpdated(tasks)
        }

        holder.editButton.setOnClickListener {
            val context = holder.itemView.context
            val editText = android.widget.EditText(context)
            editText.setText(tasks[position].taskName)

            val dialog = AlertDialog.Builder(context)
                .setTitle("Edit Task")
                .setView(editText)
                .setPositiveButton("Save") { _, _ ->
                    val newName = editText.text.toString()
                    if (newName.isNotEmpty() && newName.length < 50) {
                        tasks[position] = Tasks(newName)
                        notifyItemChanged(position)
                        onTasksUpdated(tasks)
                    }
                }
                .setNegativeButton("Cancel", null)
                .create()

            dialog.show()
        }
    }
}
