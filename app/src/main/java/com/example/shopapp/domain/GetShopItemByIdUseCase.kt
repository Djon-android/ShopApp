package com.example.shopapp.domain

class GetShopItemByIdUseCase(private val ShopListRepository: ShopListRepository) {

    fun getShopItemById(idShopItem: Int) = ShopListRepository.getShopItemById(idShopItem)
}