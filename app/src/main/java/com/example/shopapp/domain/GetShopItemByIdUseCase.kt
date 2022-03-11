package com.example.shopapp.domain

class GetShopItemByIdUseCase(private val ShopListRepository: ShopListRepository) {

    suspend fun getShopItemById(idShopItem: Int) = ShopListRepository.getShopItemById(idShopItem)
}