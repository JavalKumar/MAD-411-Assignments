package com.example.android_development

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException


class MainFragment : Fragment() {
//    private lateinit var
    private lateinit var recyclerView: RecyclerView
    private lateinit var expName: TextView
    private lateinit var amount: EditText
    private lateinit var addButton: Button
    private lateinit var financeBtn: Button


    private val filename = "RecyclerViewItem.json"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
//        val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.expenseList)
//        val expName = findViewById<TextView>(R.id.expenseName)
//        val amount = findViewById<EditText>(R.id.Amount)
//        val addButton = findViewById<Button>(R.id.button)
//        val financeBtn = findViewById<Button>(R.id.finance_btn)

        val expenseList : MutableList<RecyclerViewItem> = loadTasksFromFile(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = RecyclerViewAdapter(expenseList)
        recyclerView.adapter = adapter
//        expenseList.addAll(loadTasksFromFile(this))
        adapter.notifyDataSetChanged()
//        saveTasksToFile(this, list)
//        loadTasksFromFile(this)

        addButton.setOnClickListener(){
            val name = expName.text.toString().trim()
            val amount1 = amount.text.toString().trim()

            if (name.isNotEmpty() && amount1.isNotEmpty()){
                val newExpense = RecyclerViewItem(name,amount1)
                expenseList.add(newExpense)
                adapter.notifyItemInserted(expenseList.size -1)
//                expName.text.clear()
                amount.text.clear()
                saveTasksToFile(requireContext(), expenseList)

            }
        }

        financeBtn.setOnClickListener(){
            var url = "https://www.cibc.com/en/business/advice-centre/articles/financial-tips.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }




    }


    private fun saveTasksToFile(context: Context, taskList: List<RecyclerViewItem>){
        try {
            val json = Gson().toJson(taskList)
            context.openFileOutput(filename, Context.MODE_PRIVATE).use{ output -> output.write(json.toByteArray())
            }
            Log.d("FileStorage","Task saved successfully")
        }catch (e: IOException){
            Log.e("FileStorage","Error saving tasks: ${e.message}")
        }
    }


    private fun loadTasksFromFile(context: Context): MutableList<RecyclerViewItem> {
        val taskList: MutableList<RecyclerViewItem> = mutableListOf()
        try {
            val file = File(context.filesDir, filename)
            if (!file.exists()) return taskList

            val json = file.readText()
            val type = object : TypeToken<List<RecyclerViewItem>>() {}.type
            val loadedTasks: List<RecyclerViewItem> = Gson().fromJson(json, type)
            taskList.addAll(loadedTasks)

            Log.d("FileStorage", "Tasks loaded successfully")
        } catch (e: FileNotFoundException) {
            Log.e("FileStorage", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.e("FileStorage", "Error reading file: ${e.message}")
        }
        return taskList

    }


}