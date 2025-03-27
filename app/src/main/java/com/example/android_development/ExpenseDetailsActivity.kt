package com.example.android_development

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ExpenseDetailsActivity:AppCompatActivity(){
//    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.detail_view)

    val name = intent.getStringExtra("Expense Name")
    val amount = intent.getStringExtra("Expense Amount")

    if (name == null || amount == null) {
        Log.e("ExpenseDetailsActivity", "Missing extras!")
    } else {
        val nameView: TextView = findViewById(R.id.detail_exp_name)
        val amountView: TextView = findViewById(R.id.detail_Exp_Amount)
        nameView.text = "Expense Name: $name"
        amountView.text = "Expense Amount: $amount"
    }

}
}