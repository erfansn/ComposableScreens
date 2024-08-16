package ir.erfansn.composablescreens.food.feature.home

import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.data.Product
import ir.erfansn.composablescreens.food.data.ProductProvider

class HomeViewModel : ViewModel() {

    val vitrineItems = ProductProvider.getProductList()
        .map(Product::toVitrineItem)
}
