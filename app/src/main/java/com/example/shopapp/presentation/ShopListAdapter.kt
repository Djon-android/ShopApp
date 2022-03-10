package com.example.shopapp.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shopapp.R
import com.example.shopapp.databinding.ItemShopDisabledBinding
import com.example.shopapp.databinding.ItemShopEnabledBinding
import com.example.shopapp.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val IS_ENABLED_VIEW_TYPE = 1
        const val IS_DISABLED_VIEW_TYPE = 0
        const val MAX_POOL_SIZE = 15
    }

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val viewId = when (viewType) {
            IS_ENABLED_VIEW_TYPE -> R.layout.item_shop_enabled
            IS_DISABLED_VIEW_TYPE -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewId,
            parent,
            false
        )
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
        when(binding) {
            is ItemShopDisabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
            is ItemShopEnabledBinding -> {
                binding.tvName.text = shopItem.name
                binding.tvCount.text = shopItem.count.toString()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            IS_ENABLED_VIEW_TYPE
        } else {
            IS_DISABLED_VIEW_TYPE
        }
    }
}