package com.example.shopapp.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.shopapp.data.ShopListRepositoryImpl
import com.example.shopapp.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopListLD = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }

    }

    fun editShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            val shopCopy = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemUseCase.editShopItem(shopCopy)
        }

    }
}