package com.example.todo.ui.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainFragment : Fragment() {
    private var adapter: CustomAdapter? = null;
    private val preferencesName: String = "tasks";
    private val tasksKey: String = "tasks_json";

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleView = requireView().findViewById<RecyclerView>(R.id.todoList);
        recycleView.layoutManager = LinearLayoutManager(requireView().context)

        val data = loadTasks();

        adapter = CustomAdapter(data, this::saveTasks);
        recycleView.adapter = adapter

        val newTaskBtn = requireView().findViewById<Button>(R.id.addNewTask);
        newTaskBtn.setOnClickListener {
            addTask();
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun addTask() {
        val text: EditText = requireView().findViewById(R.id.newTaskName);

        val textStr = text.text.toString();

        if(textStr.isNotEmpty()) {
            adapter?.addTask(textStr);
        }

        text.text.clear();
        text.clearFocus();
    }

    private fun saveTasks() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(preferencesName, MODE_PRIVATE);
        val editor: SharedPreferences.Editor = sharedPreferences.edit();

        val gson: Gson = Gson();
        val json: String = gson.toJson(adapter?.getTasksList());

        editor.putString(tasksKey, json);
        editor.apply();
    }

    private fun loadTasks(): ArrayList<ItemsViewModel> {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(preferencesName, MODE_PRIVATE);
        val gson: Gson = Gson();

        val json: String? = sharedPreferences.getString(tasksKey, "[]");

        val type: Type = object : TypeToken<ArrayList<ItemsViewModel?>?>() {}.type;

        val itemsList: ArrayList<ItemsViewModel> = gson.fromJson(json, type);

        return itemsList;
    }
}