package com.example.shopapp.domain

class EditShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    suspend fun editShopItem(shopItem: ShopItem) = ShopListRepository.editShopItem(shopItem)
}