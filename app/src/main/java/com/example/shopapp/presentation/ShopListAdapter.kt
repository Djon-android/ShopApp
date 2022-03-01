package com.example.shopapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopViewHolder>() {

    companion object {
        private const val IS_ENABLED_VIEW_TYPE = 1
        private const val IS_DISABLED_VIEW_TYPE = 0
    }

    var shopList: List<ShopItem> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    inner class ShopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvCount = itemView.findViewById<TextView>(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val viewId = if (viewType == IS_ENABLED_VIEW_TYPE) {
            R.layout.item_shop_enabled
        } else {
            R.layout.item_shop_disabled
        }
        val view: View = LayoutInflater.from(parent.context).inflate(viewId, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.tvName.text = shopList[position].name
        holder.tvCount.text = shopList[position].count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) {
            IS_ENABLED_VIEW_TYPE
        } else {
            IS_DISABLED_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }
}