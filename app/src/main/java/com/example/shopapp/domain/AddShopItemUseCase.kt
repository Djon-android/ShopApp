package com.example.shopapp.domain

class AddShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    suspend fun addShopItem(shopItem: ShopItem) = ShopListRepository.addShopItem(shopItem)
}