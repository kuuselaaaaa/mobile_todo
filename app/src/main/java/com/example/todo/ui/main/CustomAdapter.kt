package com.example.todo.ui.main

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R

class CustomAdapter(private val taskList: MutableList<ItemsViewModel>, private val saveData: () -> Unit) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = taskList[position]

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text

        holder.bindDeleteButton(position);
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return taskList.size
    }

    fun addTask(text: String) {
        taskList.add(ItemsViewModel(text));

        saveData();

        notifyDataSetChanged()
    }

    fun deleteTask(index: Int) {
        taskList.removeAt(index);

        saveData();

        notifyDataSetChanged();
    }

    fun getTasksList(): MutableList<ItemsViewModel> {
        return taskList;
    }

    // Holds the views for adding it to image and text
    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.taskName);

        fun bindDeleteButton(index: Int) {
            val deleteButton = itemView.findViewById<Button>(R.id.deleteTask);

            deleteButton.setOnClickListener {
                deleteTask(index);
            }
        }
    }
}