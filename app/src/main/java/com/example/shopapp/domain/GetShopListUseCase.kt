package com.example.shopapp.domain

class GetShopListUseCase(private val ShopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem> = ShopListRepository.getShopList()
}