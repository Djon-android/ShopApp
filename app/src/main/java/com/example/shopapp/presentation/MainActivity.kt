package com.example.shopapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shopapp.R
import com.example.shopapp.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getShopList().observe(this) {
            Log.i("pussy", it.toString())
        }
        viewModel.deleteShopItem(ShopItem("name 2", 2, true, 2))
        viewModel.editShopItem(ShopItem("name 0", 0, true, 0))

    }
}