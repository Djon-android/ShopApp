package com.example.shopapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.ShopListRepositoryImpl
import com.example.shopapp.domain.DeleteShopItemUseCase
import com.example.shopapp.domain.EditShopItemUseCase
import com.example.shopapp.domain.GetShopListUseCase
import com.example.shopapp.domain.ShopItem

class MainViewModel: ViewModel() {

    private var repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopListLD = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) {
        val shopCopy = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopCopy)
    }
}