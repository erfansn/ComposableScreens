package ir.erfansn.composablescreens.food.feature.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.data.CartStorage
import ir.erfansn.composablescreens.food.data.ProductProvider

class ProductViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val productId = requireNotNull(savedStateHandle.get<Int>("id"))

    val product = ProductProvider.getProductList().first {
        it.id == productId
    }

    var quantity by mutableIntStateOf(CartStorage.storage.getOrDefault(productId, 0))
        private set

    fun changeProductQuantity(value: Int) {
        CartStorage.storage[productId] = value
        quantity = CartStorage.storage[productId]
    }
}
