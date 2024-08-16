package ir.erfansn.composablescreens.food.feature.cart

import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.data.CartStorage
import ir.erfansn.composablescreens.food.data.ProductProvider

class CartViewModel : ViewModel() {

    val cartProducts = buildList {
        val productsList = ProductProvider.getProductList()
        CartStorage.storage.forEach { id, quantity ->
            this += productsList.first { it.id == id }.toCartProduct(quantity)
        }
    }
}
