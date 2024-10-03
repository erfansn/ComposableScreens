package ir.erfansn.composablescreens.food.feature.cart

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.data.CartStorage
import ir.erfansn.composablescreens.food.data.ProductProvider

internal class CartViewModel : ViewModel() {

    val cartProducts get() = buildList {
        val productsList = ProductProvider.getProductList()
        CartStorage.storage.forEach { id, quantity ->
            this += productsList.first { it.id == id }.toCartProduct(quantity)
        }
    }

    val productsTotalPrice get() = cartProducts.fastSumBy {
        it.totalPrice
    }
}
