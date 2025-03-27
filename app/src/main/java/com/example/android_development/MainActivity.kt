package com.example.android_development

import android.annotation.SuppressLint
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

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        Log.d("LifeCycle", "onCreate was asked to come")

        val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.expenseList)
        val expName = findViewById<TextView>(R.id.expenseName)
        val amount = findViewById<EditText>(R.id.Amount)
        val addButton = findViewById<Button>(R.id.button)
        val financeBtn = findViewById<Button>(R.id.finance_btn)

        val expenseList : MutableList<RecyclerViewItem> = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = RecyclerViewAdapter(expenseList)
        recyclerView.adapter = adapter

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
            }
        }

        financeBtn.setOnClickListener(){
            var url = "https://www.cibc.com/en/business/advice-centre/articles/financial-tips.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }



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


