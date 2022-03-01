package com.example.shopapp.domain

interface ShopListRepository {

    fun getShopList(): List<ShopItem>

    fun addShopItem(shopItem: ShopItem)

    fun editShopItem(idShopItem: Int)

    fun deleteShopItem(shopItem: ShopItem)

    fun getShopItemById(idShopItem: Int): ShopItem
}