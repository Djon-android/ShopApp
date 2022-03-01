package com.example.shopapp.domain

class EditShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    fun editShopItem(shopItem: ShopItem) = ShopListRepository.editShopItem(shopItem)
}