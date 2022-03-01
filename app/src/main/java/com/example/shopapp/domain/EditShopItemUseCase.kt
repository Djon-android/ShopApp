package com.example.shopapp.domain

class EditShopItemUseCase(private val ShopListRepository: ShopListRepository) {

    fun editShopItem(idShopItem: Int) = ShopListRepository.editShopItem(idShopItem)
}