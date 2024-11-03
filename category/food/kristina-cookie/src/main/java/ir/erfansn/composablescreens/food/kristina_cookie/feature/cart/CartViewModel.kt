/*
 * Copyright 2024 Erfan Sn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ir.erfansn.composablescreens.food.kristina_cookie.feature.cart

import androidx.compose.ui.util.fastSumBy
import androidx.lifecycle.ViewModel
import ir.erfansn.composablescreens.food.kristina_cookie.data.CartStorage
import ir.erfansn.composablescreens.food.kristina_cookie.data.ProductProvider

internal class CartViewModel : ViewModel() {
  val cartProducts get() =
    buildList {
      val productsList = ProductProvider.getProductList()
      CartStorage.storage.forEach { id, quantity ->
        this += productsList.first { it.id == id }.toCartProduct(quantity)
      }
    }

  val productsTotalPrice get() =
    cartProducts.fastSumBy {
      it.totalPrice
    }
}
