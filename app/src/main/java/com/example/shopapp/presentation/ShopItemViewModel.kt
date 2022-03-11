package com.example.shopapp.presentation

import android.app.Application
import androidx.lifecycle.*
import com.example.shopapp.data.ShopListRepositoryImpl
import com.example.shopapp.domain.AddShopItemUseCase
import com.example.shopapp.domain.EditShopItemUseCase
import com.example.shopapp.domain.GetShopItemByIdUseCase
import com.example.shopapp.domain.ShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _editShopItem = MutableLiveData<ShopItem>()
    val editShopItem: LiveData<ShopItem>
        get() = _editShopItem

    private val _successLiveData = MutableLiveData<Unit>()
    val successLiveData: LiveData<Unit>
        get() = _successLiveData

    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItemById(idShopItem: Int) {
        viewModelScope.launch {
            val item = getShopItemByIdUseCase.getShopItemById(idShopItem)
            _editShopItem.value = item
        }

    }

    fun addShopItem(inputName: String?, inputCount: String?) {
            val name = parseName(inputName)
            val count = parseCount(inputCount)
            val fieldsValid = validateInput(name, count)
            if (fieldsValid) {
                viewModelScope.launch {
                    val shopItem = ShopItem(name, count, true)
                    addShopItemUseCase.addShopItem(shopItem)
                    setSuccessLD()
                }

            }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            editShopItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    setSuccessLD()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun setSuccessLD() {
        _successLiveData.value = Unit
    }
}