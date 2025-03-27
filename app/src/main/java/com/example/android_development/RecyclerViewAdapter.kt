package com.example.android_development

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter (private val dataset: MutableList<RecyclerViewItem>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view){
        val expenseName : TextView = itemView.findViewById(R.id.expenseText)
        val amount : TextView = itemView.findViewById(R.id.amountText)
        val deleteBtn : Button = itemView.findViewById(R.id.delete_btn)
        val detailBtn : Button = itemView.findViewById(R.id.detailBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = dataset[position]
        holder.expenseName.text = expense.expense
        holder.amount.text = expense.amount

        holder.deleteBtn.setOnClickListener {
            deleteExpense(position)
        }
        holder.detailBtn.setOnClickListener(){
            val context = holder.itemView.context
            val intent = Intent(context,ExpenseDetailsActivity::class.java)
            intent.putExtra("Expense Name", expense.expense)
            intent.putExtra("Expense Amount", expense.amount)
            context.startActivity(intent)
        }
    }
    private fun deleteExpense(position: Int){
        dataset.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataset.size)

    }



    override fun getItemCount() = dataset.size






}