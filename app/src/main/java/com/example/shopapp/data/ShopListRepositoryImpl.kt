package com.example.shopapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shopapp.domain.ShopItem
import com.example.shopapp.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id compareTo o2.id })

    private var shopListLD = MutableLiveData<List<ShopItem>>()

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val shopItem = ShopItem("name $i", i, Random.nextBoolean())
            addShopItem(shopItem)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateLD()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateLD()
    }


    override fun editShopItem(shopItem: ShopItem) {
//        deleteShopItem(getShopItemById(shopItem.id))
//        addShopItem(shopItem)
        val oldElement = getShopItemById(shopItem.id)
        shopList.remove(oldElement)
        addShopItem(shopItem)
    }



    override fun getShopItemById(idShopItem: Int): ShopItem {
        return shopList.find { it.id == idShopItem } ?: throw RuntimeException("Element with id $shopList not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    private fun updateLD() {
        shopListLD.value = shopList.toList()
    }




}