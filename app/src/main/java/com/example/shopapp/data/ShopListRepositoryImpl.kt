package com.example.shopapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopapp.domain.ShopItem
import com.example.shopapp.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var mutableLiveData = MutableLiveData<List<ShopItem>>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val shopItem = ShopItem("name $i", i, true)
            addShopItem(shopItem)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        mutableLiveData.value = shopList
        return mutableLiveData
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        deleteShopItem(getShopItemById(shopItem.id))
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun getShopItemById(idShopItem: Int): ShopItem {
        return shopList.find { it.id == idShopItem } ?: throw RuntimeException("Element with id $shopList not found")
    }


}