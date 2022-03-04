package com.example.shopapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapp.data.ShopListRepositoryImpl
import com.example.shopapp.domain.*

class MainViewModel: ViewModel() {

    private var repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)

    val shopListLD = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) {
        val shopCopy = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(shopCopy)
    }

    fun getShopItemById(idShopItem: Int): ShopItem {
        return getShopItemByIdUseCase.getShopItemById(idShopItem)
    }
}