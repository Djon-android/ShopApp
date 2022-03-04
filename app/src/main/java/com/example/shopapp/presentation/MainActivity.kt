package com.example.shopapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListLD.observe(this) {
            adapterShopList.shopList = it
        }
        recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        setupRecyclerView()
        var itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    val swipe = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteShopItem(viewHolder.itemView.)
        }
    }

//    ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
//    itemTouchhelper.attachToRecyclerView(recyclerView);



    private fun setupRecyclerView() {

        adapterShopList = ShopListAdapter()

            recyclerView.adapter = adapterShopList
            recyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.IS_ENABLED_VIEW_TYPE, ShopListAdapter.MAX_POOL_SIZE)
            recyclerView.recycledViewPool.setMaxRecycledViews(ShopListAdapter.IS_DISABLED_VIEW_TYPE, ShopListAdapter.MAX_POOL_SIZE)

        adapterShopList.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
        adapterShopList.onShopItemClickListener = {
            Log.i("pussy", it.toString())
        }
    }


}