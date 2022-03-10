package com.example.shopapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditiningFinishedListener{
    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Jopa", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterShopList: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopListLD.observe(this) {
            adapterShopList.submitList(it)
        }
        binding.buttonAddShopItem.setOnClickListener {
            if (!isOnePaneMode()) {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            } else {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }


    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapterShopList = ShopListAdapter()
        with(recyclerView) {
            adapter = adapterShopList
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_ENABLED_VIEW_TYPE,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.IS_DISABLED_VIEW_TYPE,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(recyclerView)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        val swipe =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val shopItem = adapterShopList.currentList[viewHolder.adapterPosition]
                    viewModel.deleteShopItem(shopItem)
                }
            }
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupClickListener() {
        adapterShopList.onShopItemClickListener = {
            if (!isOnePaneMode()) {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            } else {
                val intent = ShopItemActivity.newIntentEditItem(this@MainActivity, it.id)
                startActivity(intent)
            }
        }
    }

    private fun setupLongClickListener() {
        adapterShopList.onShopItemLongClickListener = {
            viewModel.editShopItem(it)
        }
    }
}