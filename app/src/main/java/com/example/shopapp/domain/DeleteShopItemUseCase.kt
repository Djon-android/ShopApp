package com.example.shopapp.domain

class DeleteShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        ShopListRepository.deleteShopItem(shopItem)
    }
}