package ir.erfansn.composablescreens.food.kristina_cookie.feature.home

import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.data.Product
import ir.erfansn.composablescreens.food.kristina_cookie.data.ProductProvider

internal class HomeViewModel : ViewModel() {

    val vitrineItems = ProductProvider.getProductList()
        .map(Product::toVitrineItem)
}
