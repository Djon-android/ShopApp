package com.example.shopapp.domain

class AddShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    fun addShopItem(shopItem: ShopItem) = ShopListRepository.addShopItem(shopItem)
}