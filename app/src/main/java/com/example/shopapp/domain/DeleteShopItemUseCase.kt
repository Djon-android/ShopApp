package com.example.shopapp.domain

class DeleteShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    fun deleteShopItem(shopItem: ShopItem) = ShopListRepository.deleteShopItem(shopItem)
}