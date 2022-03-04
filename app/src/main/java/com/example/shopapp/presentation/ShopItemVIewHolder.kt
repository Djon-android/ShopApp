package com.example.shopapp.presentation

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R

class ShopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_name)
    val tvCount = itemView.findViewById<TextView>(R.id.tv_count)
}