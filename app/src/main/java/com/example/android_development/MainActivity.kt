package com.example.android_development

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val filename = "RecyclerViewItem.json"
    private val list = mutableListOf<RecyclerViewItem>()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d("LifeCycle", "onCreate was asked to come")
        val header = Header()
        val footer = Footer()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,header)
            .replace(R.id.footerContainer,footer)
            .commit()

        val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.expenseList)
        val expName = findViewById<TextView>(R.id.expenseName)
        val amount = findViewById<EditText>(R.id.Amount)
        val addButton = findViewById<Button>(R.id.button)
        val financeBtn = findViewById<Button>(R.id.finance_btn)

        val expenseList : MutableList<RecyclerViewItem> = loadTasksFromFile(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = RecyclerViewAdapter(expenseList)
        recyclerView.adapter = adapter
//        expenseList.addAll(loadTasksFromFile(this))
        adapter.notifyDataSetChanged()
//        saveTasksToFile(this, list)
//        loadTasksFromFile(this)

        addButton.setOnClickListener(){
            val name = expName.text.toString().trim()
            val amount1 = amount.text.toString().trim()

//            if(name.isEmpty()){
//                val newName = RecyclerViewItem(name)
//                expenseList.add(newName)
//                adapter.notifyItemInserted(expenseList.size -1)
////                expName.text.clear()
//                if (amount1.isEmpty()){
//                    val newAmount = RecyclerViewItem(amount1)
//                    expenseList.add(newAmount)
//                    adapter.notifyItemInserted(expenseList.size -1)
//                    amount.text.clear()
//                }
//            }

            if (name.isNotEmpty() && amount1.isNotEmpty()){
                val newExpense = RecyclerViewItem(name,amount1)
                expenseList.add(newExpense)
                adapter.notifyItemInserted(expenseList.size -1)
//                expName.text.clear()
                amount.text.clear()
                saveTasksToFile(this, expenseList)

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
            context.openFileOutput(filename, Context.MODE_PRIVATE).use{output -> output.write(json.toByteArray())
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
    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle","onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy called")
    }





}


